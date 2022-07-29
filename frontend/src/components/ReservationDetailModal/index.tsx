import { useState, useEffect } from 'react';

import * as S from './styled';

import Modal from '../../components/@common/Modal';
import Accordion from '../../components/@common/Accordion';

import useModal from '../../components/@common/Modal/useModal';

import { ReservationType } from '../../types/domain';
import { getReservationAPI } from '../../api';
import { getDateString, getTimeString } from '../../utils';

const deleteIconURL =
  'https://icons-for-free.com/download-icon-delete+remove+trash+trash+bin+trash+can+icon-1320073117929397588_512.png';
const closeIconURL = 'https://icons-for-free.com/download-icon-CLOSE-131994911256789607_512.png';
const calendarIconURL = 'https://cdn-icons-png.flaticon.com/512/7088/7088625.png';
const timeIconURL = 'https://cdn-icons-png.flaticon.com/512/88/88291.png';
const coachImageURL =
  'https://cdn-icons.flaticon.com/png/512/4623/premium/4623628.png?token=exp=1658972292~hmac=d7731fb9dea54ad0251f9d08247cf4e7';

type ReservationDetailModalProps = {
  role: 'coach' | 'crew';
  reservationId: boolean;
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
        <S.Icon src={deleteIconURL} alt="삭제 아이콘" active />
        <S.Icon
          src={closeIconURL}
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
              <S.Icon src={coachImageURL} alt="코치 아이콘" />
            </S.IconBox>
            <p>{reservation?.coachNickname}</p>
          </S.Info>
        )}
        <S.Info>
          <S.IconBox>
            <S.Icon src={calendarIconURL} alt="달력 아이콘" />
          </S.IconBox>
          <p>{reservation && getDateString(reservation.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <S.IconBox>
            <S.Icon src={timeIconURL} alt="시간 아이콘" />
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
