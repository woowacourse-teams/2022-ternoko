import React, { useEffect, useMemo, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import * as S from './styled';

import ApplyInterviewCalendar from '@/Crew/components/ApplyInterviewCalendar';
import Profile from '@/Crew/components/Profile';

import Button from '@/Shared/components/Button/styled';
import Header from '@/Shared/components/Header';
import TextAreaField from '@/Shared/components/TextAreaField';
import resizeTextArea from '@/Shared/components/TextAreaField/resizeTextArea';
import Time from '@/Shared/components/Time/styled';
import TitleBox from '@/Shared/components/TitleBox';

import {
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/Shared/context/CalendarProvider';
import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';

import { getCoachScheduleAPI } from '@/Coach/api';

import {
  getCoachScheduleAndUsedScheduleAPI,
  getCoachesAPI,
  getInterviewAPI,
  postInterviewAPI,
  putInterviewAPI,
} from '@/Crew/api';
import { CREW_APPLY_FORM_MAX_LENGTH } from '@/Crew/constants';
import { isValidApplyFormLength } from '@/Crew/validation';

import { INITIAL_NUMBER_STATE } from '@/Shared/constants';
import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants/message';
import { PATH } from '@/Shared/constants/path';
import { separateFullDate } from '@/Shared/utils';

import { AvailableTimeType, CoachType, StringDictionaryType } from '@/Types/domain';

export type StepStatusType = 'show' | 'hidden' | 'onlyShowTitle';

type ServerTimeType = { id: number; time: string };

type AvailableScheduleType = StringDictionaryType<ServerTimeType>;

const CrewInterviewApplyPage = () => {
  const navigate = useNavigate();
  const { showToast } = useToastActions();

  const { onLoading, offLoading } = useLoadingActions();

  const { search } = useLocation();
  const interviewId = new URLSearchParams(search).get('interviewId');

  const { year, month, selectedDates } = useCalendarState();
  const {
    initializeYearMonth,
    removeDate,
    addOrSetDateBySelectMode,
    resetSelectedDates,
    addSelectedDates,
  } = useCalendarActions();
  const { getDateStrings, isSameDate, isSelectedDate } = useCalendarUtils();

  const [stepStatus, setStepStatus] = useState<StepStatusType[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<CoachType[]>([]);
  const [availableSchedules, setAvailableSchedules] = useState<AvailableScheduleType>({});
  const [availableTimes, setAvailableTimes] = useState<ServerTimeType[]>([]);

  const [coachId, setCoachId] = useState(INITIAL_NUMBER_STATE);
  const [availableDateTimeId, setAvailableDateTimeId] = useState(INITIAL_NUMBER_STATE);
  const [answer1, setAnswer1] = useState('');
  const [answer2, setAnswer2] = useState('');
  const [answer3, setAnswer3] = useState('');

  const changeCoachIdRef = useRef(false);
  const originCoachIdRef = useRef(INITIAL_NUMBER_STATE);
  const initRef = useRef(true);
  const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const rerenderCondition = useMemo(() => Date.now(), [stepStatus[1]]);
  const timeRerenderKey = useMemo(() => Date.now(), [selectedDates]);

  const isNotValidForm =
    !isValidApplyFormLength(answer1) ||
    !isValidApplyFormLength(answer2) ||
    !isValidApplyFormLength(answer3);

  const selectedTime = availableTimes.find(({ id }) => id === availableDateTimeId)?.time;

  const getDayType = (day: number) =>
    selectedDates.length && isSameDate(selectedDates[0], day)
      ? 'active'
      : availableSchedules[day]
      ? 'default'
      : 'disable';

  const coachScheduleAPI = () =>
    interviewId && coachId === originCoachIdRef.current
      ? getCoachScheduleAndUsedScheduleAPI(Number(interviewId), coachId, year, month)
      : getCoachScheduleAPI(coachId, year, month);

  const updateStatusWhenCalendarShow = (calendarTimes: AvailableTimeType[]) => {
    const schedules = calendarTimes.reduce((acc, { id, calendarTime }: AvailableTimeType) => {
      const { day, time } = separateFullDate(calendarTime);

      acc[Number(day)] = acc[Number(day)] ? [...acc[Number(day)], { id, time }] : [{ id, time }];

      return acc;
    }, {} as AvailableScheduleType);

    setAvailableSchedules(schedules);

    if (changeCoachIdRef.current) {
      resetSelectedDates();
      setAvailableTimes([]);
      setAvailableDateTimeId(INITIAL_NUMBER_STATE);
    } else if (interviewId && initRef.current) {
      schedules[selectedDates[0].day] || resetSelectedDates();

      const usedCalendarTime = calendarTimes.find(({ status }) => status === 'USED');

      usedCalendarTime && setAvailableDateTimeId(usedCalendarTime.id);

      setAvailableTimes(schedules[selectedDates[0].day] ?? []);
    }

    initRef.current = false;
  };

  const handleClickStepTitle = (step: number) => {
    setStepStatus((prevStepStatusType) =>
      prevStepStatusType.map((StepStatusType, index) =>
        index === step ? 'show' : index > step ? 'hidden' : StepStatusType,
      ),
    );
  };

  const handleClickStepNextButton = (step: number) => {
    setStepStatus((prevStepStatusType) =>
      prevStepStatusType.map((StepStatusType, index) =>
        index === step ? 'onlyShowTitle' : index === step + 1 ? 'show' : StepStatusType,
      ),
    );
  };

  const getHandleClickProfile = (id: number) => () => {
    if (id === coachId) return;

    changeCoachIdRef.current = true;
    setCoachId(id);
  };

  const getHandleClickTime = (id: number) => () => setAvailableDateTimeId(id);

  const getHandleChangeAnswer =
    (setAnswer: (answer: string) => void) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setAnswer(e.target.value);
      resizeTextArea(e.target);
    };

  const getHandleClickDay = (day: number) => () => {
    const times = getDayType(day) === 'default' ? availableSchedules[day] : [];

    setAvailableTimes(times);
    setAvailableDateTimeId(INITIAL_NUMBER_STATE);

    changeCoachIdRef.current = false;

    if (isSelectedDate(day)) {
      removeDate(day);
    } else {
      addOrSetDateBySelectMode(day);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (timerRef.current) return;

    timerRef.current = setTimeout(() => (timerRef.current = null), 700);

    if (isNotValidForm) return;

    const body = {
      coachId,
      availableDateTimeId,
      interviewDatetime: `${getDateStrings()[0]} ${selectedTime}`,
      interviewQuestions: [
        {
          question: '이번 면담을 통해 논의하고 싶은 내용',
          answer: `${answer1}`,
        },
        {
          question: '최근에 자신이 긍정적으로 보는 시도와 변화',
          answer: `${answer2}`,
        },
        {
          question: '이번 면담을 통해 어떤 변화가 생기기를 원하는지',
          answer: `${answer3}`,
        },
      ],
    };

    try {
      onLoading();

      if (interviewId) {
        await putInterviewAPI(Number(interviewId), body);
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_INTERVIEW);
        navigate(`${PATH.INTERVIEW_COMPLETE}/${interviewId}`);
      } else {
        const response = await postInterviewAPI(body);
        const location = response.headers.location;

        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_INTERVIEW);
        navigate(`${PATH.INTERVIEW_COMPLETE}/${location.split('/').pop()}`);
      }
    } catch (error) {
      showToast('ERROR', ERROR_MESSAGE.CHECK_DAY_AND_TIME);
      offLoading();
    }
  };

  useEffect(() => {
    (async () => {
      const coachResponse = await getCoachesAPI();

      setCoaches(coachResponse.data.coaches);

      if (!interviewId) return;

      const interviewResponse = await getInterviewAPI(Number(interviewId));
      const { coachNickname, interviewQuestions, interviewStartTime } = interviewResponse.data;

      const coach = coachResponse.data.coaches.find(({ nickname }) => nickname === coachNickname);

      if (coach) {
        setCoachId(coach.id);
        originCoachIdRef.current = coach.id;
      }

      setAnswer1(interviewQuestions[0].answer);
      setAnswer2(interviewQuestions[1].answer);
      setAnswer3(interviewQuestions[2].answer);

      const { year, month, day } = separateFullDate(interviewStartTime);

      initializeYearMonth(Number(year), Number(month));
      addSelectedDates([{ year: Number(year), month: Number(month), day: Number(day) }]);
    })();
  }, []);

  useEffect(() => {
    if (stepStatus[1] === 'show') {
      (async () => {
        const response = await coachScheduleAPI();

        updateStatusWhenCalendarShow(response.data.calendarTimes);
      })();
    }
  }, [stepStatus, year, month]);

  return (
    <>
      <Header />
      <S.Body>
        <TitleBox to={PATH.CREW_HOME}>{interviewId ? '면담 수정하기' : '면담 신청하기'}</TitleBox>
        <S.Container>
          <S.Box stepStatus={stepStatus[0]}>
            <div className="sub-title" onClick={() => handleClickStepTitle(0)}>
              <S.Circle>1</S.Circle>
              <h3>
                코치를 선택해주세요.
                <S.EmphasizedText>
                  {coaches.find((coach) => coach.id === coachId)?.nickname ?? ''}
                </S.EmphasizedText>
              </h3>
              <S.StatusBox>
                <div>
                  <S.SmallCircle />
                  <p>한달 내 면담 불가</p>
                </div>
                <div>
                  <S.SmallCircle green />
                  <p>한달 내 면담 가능</p>
                </div>
              </S.StatusBox>
            </div>

            <div className="fold-box">
              <S.GridContainer>
                {coaches.map((coach) => (
                  <Profile
                    key={coach.id}
                    currentCoachId={coachId}
                    getHandleClickProfile={getHandleClickProfile}
                    {...coach}
                  />
                ))}
              </S.GridContainer>

              <Button
                width="100%"
                height="4rem"
                inActive={coachId === INITIAL_NUMBER_STATE}
                onClick={() => handleClickStepNextButton(0)}
              >
                다음
              </Button>
            </div>
          </S.Box>

          <S.Box stepStatus={stepStatus[1]} hideFoldBoxOverflow>
            <div className="sub-title" onClick={() => handleClickStepTitle(1)}>
              <S.Circle>2</S.Circle>
              <h3>
                날짜 및 시간을 선택해주세요.
                <S.EmphasizedText>
                  {availableDateTimeId !== INITIAL_NUMBER_STATE &&
                    `${selectedDates[0].year}년 ${selectedDates[0].month}월 ${selectedDates[0].day}일 ${selectedTime}`}
                </S.EmphasizedText>
              </h3>
            </div>

            <div className="fold-box">
              <S.DateBox>
                <ApplyInterviewCalendar
                  rerenderCondition={rerenderCondition}
                  getHandleClickDay={getHandleClickDay}
                  getDayType={getDayType}
                />

                <S.TimeContainer key={timeRerenderKey} heightUnit={availableTimes.length}>
                  {availableTimes.map(({ id, time }) => (
                    <Time
                      key={id}
                      active={id === availableDateTimeId}
                      onClick={getHandleClickTime(id)}
                    >
                      {time}
                    </Time>
                  ))}
                </S.TimeContainer>
              </S.DateBox>

              <Button
                width="100%"
                height="4rem"
                inActive={availableDateTimeId === INITIAL_NUMBER_STATE}
                onClick={() => handleClickStepNextButton(1)}
              >
                다음
              </Button>
            </div>
          </S.Box>

          <S.Box stepStatus={stepStatus[2]}>
            <div className="sub-title">
              <S.Circle>3</S.Circle>
              <h3>사전 논의 내용을 입력해주세요.</h3>
            </div>

            <div className="fold-box">
              <S.Form onSubmit={handleSubmit}>
                <TextAreaField
                  id="example1"
                  label="이번 면담을 통해 논의하고 싶은 내용"
                  value={answer1}
                  maxLength={CREW_APPLY_FORM_MAX_LENGTH}
                  message={ERROR_MESSAGE.ENTER_IN_RANGE_APPLY_FORM_LENGTH}
                  handleChange={getHandleChangeAnswer(setAnswer1)}
                  checkValidation={isValidApplyFormLength}
                />
                <TextAreaField
                  id="example2"
                  label="최근에 자신이 긍정적으로 보는 시도와 변화"
                  value={answer2}
                  maxLength={CREW_APPLY_FORM_MAX_LENGTH}
                  message={ERROR_MESSAGE.ENTER_IN_RANGE_APPLY_FORM_LENGTH}
                  handleChange={getHandleChangeAnswer(setAnswer2)}
                  checkValidation={isValidApplyFormLength}
                />
                <TextAreaField
                  id="example3"
                  label="이번 면담을 통해 어떤 변화가 생기기를 원하는지"
                  value={answer3}
                  maxLength={CREW_APPLY_FORM_MAX_LENGTH}
                  message={ERROR_MESSAGE.ENTER_IN_RANGE_APPLY_FORM_LENGTH}
                  handleChange={getHandleChangeAnswer(setAnswer3)}
                  checkValidation={isValidApplyFormLength}
                />
                <S.EmphasizedText>*사전 메일은 면담 전날 23시 59분에 발송됩니다.</S.EmphasizedText>
                <Button type="submit" width="100%" height="4rem" inActive={isNotValidForm}>
                  {interviewId ? '수정 완료' : '신청 완료'}
                </Button>
              </S.Form>
            </div>
          </S.Box>
        </S.Container>
      </S.Body>
    </>
  );
};

export default CrewInterviewApplyPage;
