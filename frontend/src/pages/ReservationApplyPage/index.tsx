import React, { useEffect, useMemo, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import GridContainer from '@/components/@common/GridContainer/styled';
import TitleBox from '@/components/@common/TitleBox';

import Calendar from '@/components/Calendar';
import CoachProfile from '@/components/CoachProfile';
import TextAreaField from '@/components/TextAreaField';
import resizeTextArea from '@/components/TextAreaField/resizeTextArea';
import Time from '@/components/Time/styled';

import useTimes from '@/hooks/useTimes';

import { useCalendarActions, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';
import { useToastActions } from '@/context/ToastProvider';

import { CoachType, ReservationType, StringDictionary } from '@/types/domain';

import {
  getCoachScheduleAPI,
  getCoachesAPI,
  getReservationAPI,
  postReservationAPI,
  putReservationAPI,
} from '@/api';
import { CREW_APPLY_FORM_MAX_LENGTH, ERROR_MESSAGE, PAGE, SUCCESS_MESSAGE } from '@/constants';
import { separateFullDate } from '@/utils';
import { isValidApplyFormLength } from '@/validations';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const INITIAL_COACH_ID = -1;

const ReservationApplyPage = () => {
  const navigate = useNavigate();
  const { showToast } = useToastActions();

  const { search } = useLocation();
  const reservationId = new URLSearchParams(search).get('reservationId');

  const { year, month, selectedDates } = useCalendarState();
  const { initializeYearMonth, setDay, resetSelectedDates } = useCalendarActions();
  const { getDateStrings, isSameDate } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime, resetTimes } = useTimes({
    selectMode: 'single',
  });

  const [stepStatus, setStepStatus] = useState<StepStatus[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<CoachType[]>([]);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [availableSchedules, setAvailableSchedules] = useState<StringDictionary>({});
  const [availableTimes, setAvailableTimes] = useState<string[]>([]);

  const [coachId, setCoachId] = useState(INITIAL_COACH_ID);
  const [answer1, setAnswer1] = useState('');
  const [answer2, setAnswer2] = useState('');
  const [answer3, setAnswer3] = useState('');

  const initRef = useRef(false);

  const rerenderCondition = useMemo(() => Date.now(), [stepStatus[1]]);
  const timeRerenderKey = useMemo(() => Date.now(), [selectedDates]);

  const getDayType = (day: number) =>
    selectedDates.length && isSameDate(selectedDates[0], day)
      ? 'active'
      : availableSchedules[day]
      ? 'default'
      : 'disable';

  const handleClickStepTitle = (step: number) => {
    setStepStatus((prevStepStatus) =>
      prevStepStatus.map((stepStatus, index) =>
        index === step ? 'show' : index > step ? 'hidden' : stepStatus,
      ),
    );
  };

  const handleClickStepNextButton = (step: number) => {
    setStepStatus((prevStepStatus) =>
      prevStepStatus.map((stepStatus, index) =>
        index === step ? 'onlyShowTitle' : index === step + 1 ? 'show' : stepStatus,
      ),
    );
  };

  const getHandleClickProfile = (id: number) => () => {
    const coach = coaches.find((coach) => coach.id === id);

    showToast('SUCCESS', SUCCESS_MESSAGE.SELECT_COACH((coach as CoachType).nickname));
    setCoachId(id);
  };

  const getHandleChangeAnswer =
    (setAnswer: (answer: string) => void) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setAnswer(e.target.value);
      resizeTextArea(e.target);
    };

  const getHandleClickDay = (day: number) => () => {
    const times = getDayType(day) === 'default' ? availableSchedules[day] : [];
    setAvailableTimes(times);
    setDay(day);
    resetTimes();
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    if (
      !isValidApplyFormLength(answer1) ||
      !isValidApplyFormLength(answer2) ||
      !isValidApplyFormLength(answer3)
    )
      return;

    const body = {
      coachId,
      interviewDatetime: `${getDateStrings()[0]} ${selectedTimes[0]}`,
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

    if (reservationId) {
      await putReservationAPI(Number(reservationId), body);
      showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_RESERVATION);
      navigate(`${PAGE.RESERVATION_COMPLETE}/${reservationId}`);
    } else {
      const response = await postReservationAPI(body);
      showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_RESERVATION);

      const location = response.headers.location;
      navigate(`${PAGE.RESERVATION_COMPLETE}/${location.split('/').pop()}`);
    }
  };

  useEffect(() => {
    (async () => {
      const coachResponse = await getCoachesAPI();
      setCoaches(coachResponse.data.coaches);

      if (!reservationId) return;

      const reservationResponse = await getReservationAPI(Number(reservationId));
      const { coachNickname, interviewQuestions, interviewStartTime }: ReservationType =
        reservationResponse.data;

      const coach = coachResponse.data.coaches.find(
        ({ nickname }: CoachType) => nickname === coachNickname,
      );
      setCoachId(coach.id);

      setAnswer1(interviewQuestions[0].answer);
      setAnswer2(interviewQuestions[1].answer);
      setAnswer3(interviewQuestions[2].answer);

      const { year, month, day } = separateFullDate(interviewStartTime);

      initializeYearMonth(Number(year), Number(month) - 1);
      setDay(Number(day));
    })();
  }, []);

  useEffect(() => {
    if (stepStatus[1] === 'show') {
      (async () => {
        const response = await getCoachScheduleAPI(coachId, year, month + 1);

        const schedules = response.data.calendarTimes.reduce(
          (acc: StringDictionary, fullDate: string) => {
            const { day, time } = separateFullDate(fullDate);

            acc[Number(day)] = acc[Number(day)] ? [...acc[Number(day)], time] : [time];

            return acc;
          },
          {} as StringDictionary,
        );

        setAvailableSchedules(schedules);

        if (!initRef.current) {
          initRef.current = true;
          reservationId && setAvailableTimes(schedules[selectedDates[0].day] ?? []);
        }
      })();
    }
  }, [stepStatus, year, month, initRef.current]);

  useEffect(() => {
    resetTimes();
    setAvailableTimes([]);
    resetSelectedDates();
  }, [year, month]);

  return (
    <>
      <TitleBox to={PAGE.CREW_HOME} title={reservationId ? '면담 수정하기' : '면담 신청하기'} />
      <S.Container>
        <S.Box stepStatus={stepStatus[0]}>
          <div className="sub-title" onClick={() => handleClickStepTitle(0)}>
            <S.Circle>1</S.Circle>
            <h3>코치를 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <GridContainer minSize="110px" isCenter={true} pb="3rem">
              {coaches.map((coach) => (
                <CoachProfile
                  key={coach.id}
                  {...coach}
                  currentCoachId={coachId}
                  getHandleClickProfile={getHandleClickProfile}
                />
              ))}
            </GridContainer>

            <Button
              width="100%"
              height="40px"
              inActive={coachId === INITIAL_COACH_ID}
              onClick={() => handleClickStepNextButton(0)}
            >
              다음
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={stepStatus[1]}>
          <div className="sub-title" onClick={() => handleClickStepTitle(1)}>
            <S.Circle>2</S.Circle>
            <h3>날짜 및 시간을 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <S.DateBox>
              <Calendar
                rerenderCondition={rerenderCondition}
                getHandleClickDay={getHandleClickDay}
                getDayType={getDayType}
              />

              <S.TimeContainer key={timeRerenderKey} heightUnit={availableTimes.length}>
                {availableTimes.map((availableTime, index) => (
                  <Time
                    key={index}
                    active={selectedTimes[0] === availableTime}
                    onClick={getHandleClickTime(availableTime)}
                  >
                    {availableTime}
                  </Time>
                ))}
              </S.TimeContainer>
            </S.DateBox>

            <Button
              width="100%"
              height="40px"
              inActive={!selectedTimes.length}
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
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example2"
                label="최근에 자신이 긍정적으로 보는 시도와 변화"
                value={answer2}
                maxLength={CREW_APPLY_FORM_MAX_LENGTH}
                message={ERROR_MESSAGE.ENTER_IN_RANGE_APPLY_FORM_LENGTH}
                handleChange={getHandleChangeAnswer(setAnswer2)}
                checkValidation={isValidApplyFormLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example3"
                label="이번 면담을 통해 어떤 변화가 생기기를 원하는지"
                value={answer3}
                maxLength={CREW_APPLY_FORM_MAX_LENGTH}
                message={ERROR_MESSAGE.ENTER_IN_RANGE_APPLY_FORM_LENGTH}
                handleChange={getHandleChangeAnswer(setAnswer3)}
                checkValidation={isValidApplyFormLength}
                isSubmitted={isSubmitted}
              />
              <Button type="submit" width="100%" height="40px">
                {reservationId ? '수정 완료' : '신청 완료'}
              </Button>
            </S.Form>
          </div>
        </S.Box>
      </S.Container>
    </>
  );
};

export default ReservationApplyPage;
