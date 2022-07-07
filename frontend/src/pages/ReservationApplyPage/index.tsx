import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

import Button from '../../components/@common/Button/styled';
import GridContainer from '../../components/@common/GridContainer/styled';
import CoachProfile from '../../components/CoachProfile';
import TextAreaField from '../../components/TextAreaField';
import Calendar from '../../components/Calendar';

import * as S from './styled';

import { Coach } from 'types/domain';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const ReservationApplyPage = () => {
  const [stepStatus, setStepStatus] = useState<StepStatus[]>(['show', 'hidden', 'hidden']);
  const [coaches, setCoaches] = useState<Coach[]>([]);
  const [currentCoachId, setCurrentCoachId] = useState<number | null>(null);

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
              <Calendar />
              <S.TimeContainer>
                <S.Time>10 : 00</S.Time>
                <S.Time>10 : 30</S.Time>
                <S.Time>11 : 00</S.Time>
                <S.Time>11 : 30</S.Time>
                <S.Time>12 : 00</S.Time>
                <S.Time>12 : 30</S.Time>
                <S.Time>13 : 00</S.Time>
                <S.Time>13 : 30</S.Time>
                <S.Time>14 : 00</S.Time>
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
            <S.Form>
              <TextAreaField id="example1" label="이번 면담을 통해 논의하고 싶은 내용" />
              <TextAreaField id="example2" label="최근에 자신이 긍정적으로 보는 시도와 변화" />
              <TextAreaField id="example3" label="이번 면담을 통해 어떤 변화가 생기기를 원하는지" />
              <Button width="100%" height="40px">
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
