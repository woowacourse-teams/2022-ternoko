import { useEffect, useState } from 'react';

import * as S from './styled';

import Accordion from '@/components/@common/Accordion';
import Modal from '@/components/@common/Modal';
import useModal from '@/components/@common/Modal/useModal';

import { ReservationType } from '@/types/domain';

import { getReservationAPI } from '@/api';
import { getDateString, getTimeString } from '@/utils';

type ReservationDetailModalProps = {
  role: 'coach' | 'crew';
  reservationId: number;
};

const ReservationDetailModal = ({ role, reservationId }: ReservationDetailModalProps) => {
  const { show, handleOpenModal, display, handleCloseModal } = useModal();
  const [reservation, setReservation] = useState<ReservationType | null>(null);

  useEffect(() => {
    handleOpenModal();

    (async () => {
      const response = await getReservationAPI(Number(reservationId));
      setReservation(response.data);
    })();
  }, []);

  return (
    <Modal
      show={show}
      display={display}
      additionalFrameStyle={S.additionalFrameStyle}
      handleCloseModal={handleCloseModal}
    >
      <S.IconContainer>
        <S.Icon src="/assets/icon/delete.png" alt="삭제 아이콘" active />
        <S.Icon
          src="/assets/icon/close.png"
          alt="모달 창 닫기 아이콘"
          active
          agg
          onClick={handleCloseModal}
        />
      </S.IconContainer>
      <S.Profile>
        <img src={reservation?.imageUrl} alt="프로필" />
        <p>{reservation?.crewNickname}</p>
      </S.Profile>
      <S.InfoContainer>
        {role === 'crew' && (
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
          <Accordion title={question} description={answer} />
        ))}
      </S.AccordionContainer>
    </Modal>
  );
};

export default ReservationDetailModal;
