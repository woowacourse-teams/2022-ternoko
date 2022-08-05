import { useEffect, useState } from 'react';

import * as S from './styled';

import Accordion from '@/components/@common/Accordion';
import Modal from '@/components/@common/Modal';

import { useToastActions } from '@/context/ToastProvider';

import { ReservationType } from '@/types/domain';
import { MemberRole } from '@/types/domain';

import { deleteCoachReservationAPI, deleteCrewReservationAPI, getReservationAPI } from '@/api';
import { SUCCESS_MESSAGE } from '@/constants';
import { getDateString, getTimeString } from '@/utils';

type ReservationDetailModalProps = {
  show: boolean;
  display: boolean;
  role: MemberRole;
  reservationId: number;
  handleCloseModal: () => void;
};

const ReservationDetailModal = ({
  show,
  display,
  role,
  reservationId,
  handleCloseModal,
}: ReservationDetailModalProps) => {
  const [reservation, setReservation] = useState<ReservationType | null>();

  const { showToast } = useToastActions();

  const handleClickDeleteButton = () => {
    if (confirm('정말로 삭제하시겠습니까?')) {
      if (role === 'CREW') {
        deleteCrewReservationAPI(reservationId);
        showToast('SUCCESS', SUCCESS_MESSAGE.CREW_DELETE_RESERVATION);
      } else {
        deleteCoachReservationAPI(reservationId);
        showToast('SUCCESS', SUCCESS_MESSAGE.COACH_DELETE_RESERVATION);
      }
    }
  };

  useEffect(() => {
    if (!show) return;

    (async () => {
      const response = await getReservationAPI(Number(reservationId));
      setReservation(response.data);
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
        <img src={reservation?.crewImageUrl} alt="프로필" />
        <p>{reservation?.crewNickname}</p>
      </S.Profile>
      <S.InfoContainer>
        {role === 'CREW' && (
          <S.Info>
            <S.IconBox>
              <S.Icon src="/assets/icon/human.png" alt="코치 아이콘" />
            </S.IconBox>
            <p>{reservation?.coachNickname}</p>
          </S.Info>
        )}
        <S.Info>
          <S.IconBox>
            <S.Icon src="/assets/icon/calendar.png" alt="달력 아이콘" />
          </S.IconBox>
          <p>{reservation && getDateString(reservation.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <S.IconBox>
            <S.Icon src="/assets/icon/clock.png" alt="시간 아이콘" />
          </S.IconBox>
          <p>
            {reservation &&
              `${getTimeString(reservation.interviewEndTime)} ~ ${getTimeString(
                reservation.interviewEndTime,
              )}`}
          </p>
        </S.Info>
      </S.InfoContainer>
      <S.AccordionContainer>
        {reservation?.interviewQuestions.map(({ question, answer }) => (
          <Accordion key={question} title={question} description={answer} />
        ))}
      </S.AccordionContainer>
    </Modal>
  );
};

export default ReservationDetailModal;
