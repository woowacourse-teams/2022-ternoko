import { useEffect, useState } from 'react';

import * as S from './styled';

import Accordion from '@/components/@common/Accordion';
import Modal from '@/components/@common/Modal';

import { useToastActions } from '@/context/ToastProvider';

import { InterviewType } from '@/types/domain';
import { MemberRole } from '@/types/domain';

import { deleteCoachInterviewAPI, deleteCrewInterviewAPI, getInterviewAPI } from '@/api';
import { SUCCESS_MESSAGE } from '@/constants';
import { getDateString, getTimeString } from '@/utils';

type InterviewDetailModalProps = {
  show: boolean;
  display: boolean;
  role: MemberRole;
  interviewId: number;
  handleCloseModal: () => void;
  afterDeleteInterview: () => void;
};

const InterviewDetailModal = ({
  show,
  display,
  role,
  interviewId,
  handleCloseModal,
  afterDeleteInterview,
}: InterviewDetailModalProps) => {
  const [interview, setInterview] = useState<InterviewType | null>();

  const { showToast } = useToastActions();

  const handleClickDeleteButton = async () => {
    if (confirm('정말로 삭제하시겠습니까?')) {
      if (role === 'CREW') {
        await deleteCrewInterviewAPI(interviewId);
        showToast('SUCCESS', SUCCESS_MESSAGE.CREW_DELETE_RESERVATION);
      } else {
        await deleteCoachInterviewAPI(interviewId);
        showToast('SUCCESS', SUCCESS_MESSAGE.COACH_DELETE_RESERVATION);
      }

      afterDeleteInterview();
    }
  };

  useEffect(() => {
    if (!show) return;

    (async () => {
      const response = await getInterviewAPI(Number(interviewId));
      setInterview(response.data);
    })();
  }, [show]);

  return (
    <Modal
      show={show}
      display={display}
      additionalFrameStyle={S.additionalFrameStyle}
      handleCloseModal={handleCloseModal}
    >
      <S.IconContainer>
        <S.Icon
          src="/assets/icon/delete.png"
          alt="삭제 아이콘"
          active
          onClick={handleClickDeleteButton}
        />
        <S.Icon
          src="/assets/icon/close.png"
          alt="모달 창 닫기 아이콘"
          active
          agg
          onClick={handleCloseModal}
        />
      </S.IconContainer>
      <S.Profile>
        <img src={interview?.crewImageUrl} alt="프로필" />
        <p>{interview?.crewNickname}</p>
      </S.Profile>
      <S.InfoContainer>
        {role === 'CREW' && (
          <S.Info>
            <S.IconBox>
              <S.Icon src="/assets/icon/human.png" alt="코치 아이콘" />
            </S.IconBox>
            <p>{interview?.coachNickname}</p>
          </S.Info>
        )}
        <S.Info>
          <S.IconBox>
            <S.Icon src="/assets/icon/calendar.png" alt="달력 아이콘" />
          </S.IconBox>
          <p>{interview && getDateString(interview.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <S.IconBox>
            <S.Icon src="/assets/icon/clock.png" alt="시간 아이콘" />
          </S.IconBox>
          <p>
            {interview &&
              `${getTimeString(interview.interviewEndTime)} ~ ${getTimeString(
                interview.interviewEndTime,
              )}`}
          </p>
        </S.Info>
      </S.InfoContainer>
      <S.AccordionContainer>
        {interview?.interviewQuestions.map(({ question, answer }) => (
          <Accordion key={question} title={question} description={answer} />
        ))}
      </S.AccordionContainer>
    </Modal>
  );
};

export default InterviewDetailModal;