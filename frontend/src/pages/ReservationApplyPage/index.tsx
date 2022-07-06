import { useState } from 'react';

import Button from '../../components/@common/Button/styled';
import CoachProfile from '../../components/CoachProfile';
import GridContainer from '../../components/@common/GridContainer/styled';
import * as S from './styled';
import TextAreaField from '../../components/TextAreaField';

export type StepStatus = 'show' | 'hidden' | 'onlyShowTitle';

const ReservationApplyPage = () => {
  const [step1Status, setStep1Status] = useState<StepStatus>('onlyShowTitle');
  const [step2Status, setStep2Status] = useState<StepStatus>('onlyShowTitle');
  const [step3Status, setStep3Status] = useState<StepStatus>('show');

  const handleClickStep1NextButton = () => {
    setStep1Status('onlyShowTitle');
    setStep2Status('show');
  };

  const handleClickStep1Title = () => {
    setStep1Status('show');
    setStep2Status('hidden');
    setStep3Status('hidden');
  };

  const handleClickStep2NextButton = () => {
    setStep2Status('onlyShowTitle');
    setStep3Status('show');
  };

  const handleClickStep2Title = () => {
    setStep2Status('show');
    setStep3Status('hidden');
  };

  const handleClickStep3NextButton = () => {
    //api 호출
  };

  return (
    <>
      <S.TitleBox>
        <h2>{'<'} 면담 신청하기</h2>
      </S.TitleBox>
      <S.Container>
        <S.Box stepStatus={step1Status}>
          <div className="subTitle" onClick={handleClickStep1Title}>
            <S.Circle>1</S.Circle>
            <h3>코치를 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <GridContainer minSize="110px" pb="3rem">
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
              <CoachProfile />
            </GridContainer>

            <Button width="100%" height="40px" onClick={handleClickStep1NextButton}>
              다음
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={step2Status}>
          <div className="subTitle" onClick={handleClickStep2Title}>
            <S.Circle>2</S.Circle>
            <h3>날짜 및 시간을 선택해주세요.</h3>
          </div>

          <div className="fold-box">
            <GridContainer minSize="110px" pb="3rem">
              <CoachProfile />
            </GridContainer>

            <Button width="100%" height="40px" onClick={handleClickStep2NextButton}>
              다음
            </Button>
          </div>
        </S.Box>

        <S.Box stepStatus={step3Status}>
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
