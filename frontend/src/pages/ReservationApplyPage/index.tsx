import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from '../../components/@common/Button/styled';
import GridContainer from '../../components/@common/GridContainer/styled';
import CoachProfile from '../../components/CoachProfile';
import TextAreaField from '../../components/TextAreaField';
import Calendar from '../../components/Calendar';
import useCalendar from '../../components/Calendar/useCalendar';

import * as S from './styled';

import { Coach } from 'types/domain';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const ReservationApplyPage = () => {
  const navigate = useNavigate();
  const [stepStatus, setStepStatus] = useState<StepStatus[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<Coach[]>([]);
  const [currentDay, setCurrentDay] = useState(-1);
  const [currentTime, setCurrentTime] = useState('');
  const [currentCoachId, setCurrentCoachId] = useState(-1);
  const { year: currentYear, month: currentMonth } = useCalendar();

  const [answer1, setAnswer1] = useState('');
  const [answer2, setAnswer2] = useState('');
  const [answer3, setAnswer3] = useState('');

  const [isSubmitted, setIsSubmitted] = useState(false);

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

  const handleClickProfile = (id: number) => {
    setCurrentCoachId(id);
  };

  const handleClickDay = (day: number) => () => {
    setCurrentDay(day);
  };

  const handleClickTime = (time: string) => () => {
    setCurrentTime(time);
  };

  const handleChangeAnswer =
    (setAnswer: (answer: string) => void) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setAnswer(e.target.value);
    };

  const isOverMinLength = (text: string) => {
    return text.length >= 10;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    if (!isOverMinLength(answer1) || !isOverMinLength(answer2) || !isOverMinLength(answer3)) return;

    const month = String(currentMonth).padStart(2, '0');
    const day = String(currentDay).padStart(2, '0');
    const interviewDatetime = `${currentYear}-${month}-${day} ${currentTime}:00`;

    const body = {
      interviewDatetime,
      crewNickname: '록바',
      location: '잠실',
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

    const response = await axios.post(
      `http://192.168.7.8:8080/api/reservations/coaches/${currentCoachId}`,
      body,
    );

    navigate('/reservation/complete/3');
  };

  useEffect(() => {
    (async () => {
      const response = await axios.get('http://192.168.7.8:8080/api/reservations/coaches');
      setCoaches(response.data.coaches);
    })();
  }, []);

  return (
    <>
      <S.TitleBox>
        <h2>
          <Link to="/">{'<'}</Link> 면담 신청하기
        </h2>
      </S.TitleBox>
      <S.Container>
        <S.Box stepStatus={stepStatus[0]}>
          <div className="subTitle" onClick={() => handleClickStepTitle(0)}>
            <S.Circle>1</S.Circle>
            <h3>코치를 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <GridContainer minSize="110px" pb="3rem">
              {coaches.map((coach) => (
                <CoachProfile
                  key={coach.id}
                  {...coach}
                  currentCoachId={currentCoachId}
                  handleClickProfile={handleClickProfile}
                />
              ))}
            </GridContainer>

            <Button width="100%" height="40px" onClick={() => handleClickStepNextButton(0)}>
              다음
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={stepStatus[1]}>
          <div className="subTitle" onClick={() => handleClickStepTitle(1)}>
            <S.Circle>2</S.Circle>
            <h3>날짜 및 시간을 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <S.DateBox>
              <Calendar currentDay={currentDay} handleClickDay={handleClickDay} />
              <S.TimeContainer>
                <S.Time active={currentTime === '10:00'} onClick={handleClickTime('10:00')}>
                  10 : 00
                </S.Time>
                <S.Time active={currentTime === '10:30'} onClick={handleClickTime('10:30')}>
                  10 : 30
                </S.Time>
                <S.Time active={currentTime === '11:00'} onClick={handleClickTime('11:00')}>
                  11 : 00
                </S.Time>
                <S.Time active={currentTime === '11:30'} onClick={handleClickTime('11:30')}>
                  11 : 30
                </S.Time>
                <S.Time active={currentTime === '12:00'} onClick={handleClickTime('12:00')}>
                  12 : 00
                </S.Time>
                <S.Time active={currentTime === '12:30'} onClick={handleClickTime('12:30')}>
                  12 : 30
                </S.Time>
                <S.Time active={currentTime === '13:00'} onClick={handleClickTime('13:00')}>
                  13 : 00
                </S.Time>
                <S.Time active={currentTime === '13:30'} onClick={handleClickTime('13:30')}>
                  13 : 30
                </S.Time>
                <S.Time active={currentTime === '14:00'} onClick={handleClickTime('14:00')}>
                  14 : 00
                </S.Time>
                <S.Time active={currentTime === '14:30'} onClick={handleClickTime('14:30')}>
                  14 : 30
                </S.Time>
              </S.TimeContainer>
            </S.DateBox>

            <Button width="100%" height="40px" onClick={() => handleClickStepNextButton(1)}>
              다음
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={stepStatus[2]}>
          <div className="subTitle">
            <S.Circle>3</S.Circle>
            <h3>사전 논의 내용을 입력해주세요.</h3>
          </div>

          <div className="fold-box">
            <S.Form onSubmit={handleSubmit}>
              <TextAreaField
                id="example1"
                label="이번 면담을 통해 논의하고 싶은 내용"
                answer={answer1}
                handleChange={handleChangeAnswer(setAnswer1)}
                checkValidation={isOverMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example2"
                label="최근에 자신이 긍정적으로 보는 시도와 변화"
                answer={answer2}
                handleChange={handleChangeAnswer(setAnswer2)}
                checkValidation={isOverMinLength}
                isSubmitted={isSubmitted}
              />
              <TextAreaField
                id="example3"
                label="이번 면담을 통해 어떤 변화가 생기기를 원하는지"
                answer={answer3}
                handleChange={handleChangeAnswer(setAnswer3)}
                checkValidation={isOverMinLength}
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
