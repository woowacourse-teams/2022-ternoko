import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import InterviewDetailModal from '@/components/@common/InterviewDetailModal';
import useModal from '@/components/@common/Modal/useModal';

import { InterviewType } from '@/types/domain';

import { getInterviewAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';
import { getDateString, getTimeString } from '@/utils';

const InterviewCompletePage = () => {
  const navigate = useNavigate();

  const { interviewId } = useParams();
  const memberRole = LocalStorage.getMemberRole();

  const { show, display, handleOpenModal, handleCloseModal } = useModal();
  const [interview, setInterview] = useState<InterviewType | null>(null);

  const afterDeleteInterview = () => {
    handleCloseModal();
    navigate(PAGE.CREW_HOME);
  };

  useEffect(() => {
    (async () => {
      const response = await getInterviewAPI(Number(interviewId));
      setInterview(response.data);
    })();
  }, []);

  return (
    <S.Box>
      <S.LogoBox>
        <S.Logo src={interview?.crewImageUrl} alt="로고" />
        <h2>{interview?.crewNickname}님~ 면담 신청이 완료되었습니다.</h2>
      </S.LogoBox>

      <S.InfoContainer>
        <S.Info>
          <p>코치</p>
          <p>{interview?.coachNickname}</p>
        </S.Info>
        <S.Info>
          <p>날짜</p>
          <p>{interview && getDateString(interview.interviewStartTime)}</p>

          <p>{reservation && getDateString(reservation.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>시작</p>
          <p>{interview && getTimeString(interview.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>종료</p>
          <p>{interview && getTimeString(interview.interviewEndTime)}</p>
        </S.Info>
      </S.InfoContainer>

      <S.ButtonContainer>
        <Button width="47%" height="3.5rem" white={true} onClick={handleOpenModal}>
          면담확인
        </Button>

        <Link to={PAGE.CREW_HOME}>
          <Button width="100%" height="3.5rem">
            홈으로
          </Button>
        </Link>
      </S.ButtonContainer>
      <InterviewDetailModal
        show={show}
        display={display}
        role={memberRole}
        interviewId={Number(interviewId)}
        afterDeleteInterview={afterDeleteInterview}
        handleCloseModal={handleCloseModal}
      />
    </S.Box>
  );
};

export default InterviewCompletePage;
