import { useEffect, useState } from 'react';

import * as S from './styled';

import Accordion from '@/Shared/components/Accordion';
import AskDeleteTimeModal from '@/Shared/components/AskDeleteTimeModal';
import Modal from '@/Shared/components/Modal';
import useModal from '@/Shared/components/Modal/useModal';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';

import { getInterviewAPI } from '@/Crew/api';

import { deleteCrewInterviewAPI } from '@/Shared/api';
import { CONFIRM_DELETE_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants';
import { InterviewType, MemberRoleType } from '@/Shared/types/domain';
import { getDateString, getTimeString } from '@/Shared/utils';

type InterviewDetailModalProps = {
  show: boolean;
  display: boolean;
  role: MemberRoleType;
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

  const canDelete = interview
    ? !['COMPLETED', 'CREW_COMPLETED', 'COACH_COMPLETED'].includes(interview.status)
    : false;

  const {
    show: askShow,
    display: askDisplay,
    handleOpenModal: handleAskOpenModal,
    handleCloseModal: handleAskCloseModal,
  } = useModal();

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();

  const handleClickDeleteButton = async () => {
    if (role === 'CREW' && confirm(CONFIRM_DELETE_MESSAGE)) {
      try {
        onLoading();
        await deleteCrewInterviewAPI(interviewId);
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREW_DELETE_INTERVIEW);
        afterDeleteInterview();
      } catch (e) {
        offLoading();
      }
    } else if (role === 'COACH') {
      handleAskOpenModal();
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
      additionalDimmerStyle={S.additionalDimmerStyle}
      additionalFrameStyle={S.additionalFrameStyle}
      handleCloseModal={handleCloseModal}
    >
      <S.IconContainer>
        {canDelete && (
          <picture>
            <source srcSet="/assets/icon/delete.avif" type="image/avif" />
            <S.Icon
              src="/assets/icon/delete.png"
              alt="삭제 아이콘"
              active
              onClick={handleClickDeleteButton}
            />
          </picture>
        )}
        <picture>
          <source srcSet="/assets/icon/close.avif" type="image/avif" />
          <S.Icon
            src="/assets/icon/close.png"
            alt="모달 창 닫기 아이콘"
            active
            agg
            onClick={handleCloseModal}
          />
        </picture>
      </S.IconContainer>
      <S.Profile>
        <img src={interview?.crewImageUrl} alt="프로필" />
        <p>{interview?.crewNickname}</p>
      </S.Profile>
      <S.InfoContainer>
        {role === 'CREW' && (
          <S.Info>
            <S.IconBox>
              <picture>
                <source srcSet="/assets/icon/human.avif" type="image/avif" />
                <S.Icon src="/assets/icon/human.png" alt="코치 아이콘" />
              </picture>
            </S.IconBox>

            <p>{interview?.coachNickname}</p>
          </S.Info>
        )}
        <S.Info>
          <S.IconBox>
            <picture>
              <source srcSet="/assets/icon/calendar.avif" type="image/avif" />
              <S.Icon src="/assets/icon/calendar.png" alt="달력 아이콘" />
            </picture>
          </S.IconBox>
          <p>{interview && getDateString(interview.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <S.IconBox>
            <picture>
              <source srcSet="/assets/icon/clock.avif" type="image/avif" />
              <S.Icon src="/assets/icon/clock.png" alt="시간 아이콘" />
            </picture>
          </S.IconBox>
          <p>
            {interview &&
              `${getTimeString(interview.interviewStartTime)} ~ ${getTimeString(
                interview.interviewEndTime,
              )}`}
          </p>
        </S.Info>
      </S.InfoContainer>
      <S.AccordionContainer>
        {interview?.interviewQuestions.map(({ question, answer }) => (
          <Accordion key={question} title={question} description={answer} show={show} />
        ))}
      </S.AccordionContainer>
      <AskDeleteTimeModal
        show={askShow}
        display={askDisplay}
        interviewId={interviewId}
        handleCloseModal={handleAskCloseModal}
        afterDeleteInterview={afterDeleteInterview}
      />
    </Modal>
  );
};

export default InterviewDetailModal;
