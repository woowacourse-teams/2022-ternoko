import React, { useState, useMemo, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import TitleBox from '@/components/@common/TitleBox';
import GridContainer from '@/components/@common/GridContainer/styled';
import CoachProfile from '@/components/CoachProfile';
import TextAreaField from '@/components/TextAreaField';
import Calendar from '@/components/Calendar';
import Time from '@/components/Time/styled';

import { CoachType, StringDictionary } from '@/types/domain';
import { getCoachesAPI, postReservationAPI, getCoachScheduleAPI } from '@/api';

import { useCalendarState, useCalendarActions, useCalendarUtils } from '@/context/CalendarProvider';
import useTimes from '@/hooks/useTimes';
import { separateFullDate } from '@/utils';
import { isOverApplyFormMinLength } from '@/validations';

import { PAGE } from '@/constants';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const ReservationApplyPage = () => {
  const navigate = useNavigate();
  const { year, month, selectedDates } = useCalendarState();
  const { setDay, resetSelectedDates } = useCalendarActions();
  const { getDateStrings, isSameDate } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime, resetTimes } = useTimes({ selectMode: 'single' });

  const [stepStatus, setStepStatus] = useState<StepStatus[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<CoachType[]>([]);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [availableSchedules, setAvailableSchedules] = useState<StringDictionary>({});
  const [availableTimes, setAvailableTimes] = useState<string[]>([]);

  const [coachId, setCoachId] = useState(-1);
  const [answer1, setAnswer1] = useState('');
  const [answer2, setAnswer2] = useState('');
  const [answer3, setAnswer3] = useState('');

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
    setCoachId(id);
  };

  const getHandleChangeAnswer =
    (setAnswer: (answer: string) => void) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setAnswer(e.target.value);
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
      !isOverApplyFormMinLength(answer1) ||
      !isOverApplyFormMinLength(answer2) ||
      !isOverApplyFormMinLength(answer3)
    )
      return;

    const body = {
      interviewDatetime: `${getDateStrings()[0]} ${selectedTimes[0]}`,
      crewNickname: '록바',
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

    const response = await postReservationAPI(coachId, body);
    const location = response.headers.location;

    navigate(`${PAGE.RESERVATION_COMPLETE}/${location.split('/').pop()}`);
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachesAPI();
      setCoaches(response.data.coaches);
    })();
  }, []);

  useEffect(() => {
    if (stepStatus[1] === 'show') {
      const defaultCoachId = 12;

      (async () => {
        const response = await getCoachScheduleAPI(defaultCoachId, year, month + 1);

        const schedules = response.data.calendarTimes.reduce(
          (acc: StringDictionary, fullDate: string) => {
            const { day, time } = separateFullDate(fullDate);

            acc[Number(day)] = acc[Number(day)] ? [...acc[Number(day)], time] : [time];

            return acc;
          },
          {} as StringDictionary,
        );

        setAvailableSchedules(schedules);
      })();
    }
  }, [stepStatus, year, month]);

  useEffect(() => {
    resetTimes();
    setAvailableTimes([]);
    resetSelectedDates();
  }, [year, month]);

  return (
    <>
      <TitleBox to={PAGE.CREW_HOME} title="면담 신청하기" />
      <S.Container>
        <S.Box stepStatus={stepStatus[0]}>
          <div className="sub-title" onClick={() => handleClickStepTitle(0)}>
            <S.Circle>1</S.Circle>
            <h3>코치를 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <GridContainer minSize="110px" pb="3rem">
              {coaches.map((coach) => (
                <CoachProfile
                  key={coach.id}
                  {...coach}
                  currentCoachId={coachId}
                  getHandleClickProfile={getHandleClickProfile}
                />
              ))}
            </GridContainer>

            <Button width="100%" height="40px" onClick={() => handleClickStepNextButton(0)}>
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

              <S.TimeContainer key={timeRerenderKey}>
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

            <Button width="100%" height="40px" onClick={() => handleClickStepNextButton(1)}>
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
                handleChange={getHandleChangeAnswer(setAnswer1)}
                checkValidation={isOverApplyFormMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example2"
                label="최근에 자신이 긍정적으로 보는 시도와 변화"
                value={answer2}
                handleChange={getHandleChangeAnswer(setAnswer2)}
                checkValidation={isOverApplyFormMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example3"
                label="이번 면담을 통해 어떤 변화가 생기기를 원하는지"
                value={answer3}
                handleChange={getHandleChangeAnswer(setAnswer3)}
                checkValidation={isOverApplyFormMinLength}
                isSubmitted={isSubmitted}
              />
              <Button type="submit" width="100%" height="40px">
                신청 완료
              </Button>
            </S.Form>
          </div>
        </S.Box>
      </S.Container>
    </>
  );
};

export default ReservationApplyPage;
