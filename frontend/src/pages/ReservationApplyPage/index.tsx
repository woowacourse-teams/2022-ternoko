import React, { useState, useMemo, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './styled';

import Button from '../../components/@common/Button/styled';
import TitleBox from '../../components/@common/TitleBox';
import GridContainer from '../../components/@common/GridContainer/styled';
import CoachProfile from '../../components/CoachProfile';
import TextAreaField from '../../components/TextAreaField';
import Calendar from '../../components/Calendar';
import ScrollContainer from '../../components/@common/ScrollContainer/styled';
import Time from '../../components/Time/styled';

import { CoachType } from 'types/domain';
import { getCoachesAPI, postReservationAPI } from '../../api';

import {
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '../../context/CalendarProvider';
import useTimes from '../../hooks/useTimes';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const dummyTimes = [
  '10:00',
  '10:30',
  '11:00',
  '11:30',
  '12:00',
  '12:30',
  '13:00',
  '13:30',
  '14:00',
  '14:30',
  '15:00',
  '15:30',
  '16:00',
  '16:30',
  '17:00',
  '17:30',
];

const isOverMinLength = (text: string) => {
  return text.length >= 10;
};

const ReservationApplyPage = () => {
  const navigate = useNavigate();
  const { setDay } = useCalendarActions();
  const { getDateStrings } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime } = useTimes({ selectMode: 'single' });

  const [stepStatus, setStepStatus] = useState<StepStatus[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<CoachType[]>([]);
  const [isSubmitted, setIsSubmitted] = useState(false);

  const [coachId, setCoachId] = useState(-1);
  const [answer1, setAnswer1] = useState('');
  const [answer2, setAnswer2] = useState('');
  const [answer3, setAnswer3] = useState('');

  const rerenderCondition = useMemo(() => Date.now(), [stepStatus[1]]);

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
    setDay(day);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    if (!isOverMinLength(answer1) || !isOverMinLength(answer2) || !isOverMinLength(answer3)) return;

    const body = {
      interviewDatetime: `${getDateStrings()[0]} ${selectedTimes[0]}`,
      crewNickname: '??????',
      interviewQuestions: [
        {
          question: '?????? ????????? ?????? ???????????? ?????? ??????',
          answer: `${answer1}`,
        },
        {
          question: '????????? ????????? ??????????????? ?????? ????????? ??????',
          answer: `${answer2}`,
        },
        {
          question: '?????? ????????? ?????? ?????? ????????? ???????????? ????????????',
          answer: `${answer3}`,
        },
      ],
    };

    const response = await postReservationAPI(coachId, body);
    const location = response.headers.location;

    navigate(`/reservation/complete/${location.split('/').pop()}`);
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachesAPI();
      setCoaches(response.data.coaches);
    })();
  }, []);

  return (
    <>
      <TitleBox to="/" title="?????? ????????????" />
      <S.Container>
        <S.Box stepStatus={stepStatus[0]}>
          <div className="sub-title" onClick={() => handleClickStepTitle(0)}>
            <S.Circle>1</S.Circle>
            <h3>????????? ??????????????????.</h3>
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
              ??????
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={stepStatus[1]}>
          <div className="sub-title" onClick={() => handleClickStepTitle(1)}>
            <S.Circle>2</S.Circle>
            <h3>?????? ??? ????????? ??????????????????.</h3>
          </div>

          <div className="fold-box">
            <S.DateBox>
              <Calendar
                rerenderCondition={rerenderCondition}
                getHandleClickDay={getHandleClickDay}
              />

              <ScrollContainer>
                {dummyTimes.map((dummyTime, index) => (
                  <Time
                    key={index}
                    active={selectedTimes[0] === dummyTime}
                    onClick={getHandleClickTime(dummyTime)}
                  >
                    {dummyTime}
                  </Time>
                ))}
              </ScrollContainer>
            </S.DateBox>

            <Button width="100%" height="40px" onClick={() => handleClickStepNextButton(1)}>
              ??????
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={stepStatus[2]}>
          <div className="sub-title">
            <S.Circle>3</S.Circle>
            <h3>?????? ?????? ????????? ??????????????????.</h3>
          </div>

          <div className="fold-box">
            <S.Form onSubmit={handleSubmit}>
              <TextAreaField
                id="example1"
                label="?????? ????????? ?????? ???????????? ?????? ??????"
                answer={answer1}
                handleChange={getHandleChangeAnswer(setAnswer1)}
                checkValidation={isOverMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example2"
                label="????????? ????????? ??????????????? ?????? ????????? ??????"
                answer={answer2}
                handleChange={getHandleChangeAnswer(setAnswer2)}
                checkValidation={isOverMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example3"
                label="?????? ????????? ?????? ?????? ????????? ???????????? ????????????"
                answer={answer3}
                handleChange={getHandleChangeAnswer(setAnswer3)}
                checkValidation={isOverMinLength}
                isSubmitted={isSubmitted}
              />
              <Button type="submit" width="100%" height="40px">
                ?????? ??????
              </Button>
            </S.Form>
          </div>
        </S.Box>
      </S.Container>
    </>
  );
};

export default ReservationApplyPage;
