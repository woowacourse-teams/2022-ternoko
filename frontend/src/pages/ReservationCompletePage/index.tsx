import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import useModal from '@/components/@common/Modal/useModal';
import ReservationDetailModal from '@/components/@common/ReservationDetailModal';

import { ReservationType } from '@/types/domain';

import { getReservationAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';
import { getDateString, getTimeString } from '@/utils';

const ReservationCompletePage = () => {
  const { reservationId } = useParams();
  const memberRole = LocalStorage.getMemberRole();

  const { show, display, handleOpenModal, handleCloseModal } = useModal();
  const [reservation, setReservation] = useState<ReservationType | null>(null);

  useEffect(() => {
    (async () => {
      const response = await getReservationAPI(Number(reservationId));
      setReservation(response.data);
    })();
  }, []);

  return (
    <S.Box>
      <S.LogoBox>
        <S.Logo src={reservation?.crewImageUrl} alt="로고" />
        <h2>{reservation?.crewNickname}님~ 면담 신청이 완료되었습니다.</h2>
      </S.LogoBox>

      <S.InfoContainer>
        <S.Info>
          <p>코치</p>
          <p>{reservation?.coachNickname}</p>
        </S.Info>
        <S.Info>
          <p>날짜</p>
          <p>{reservation && getDateString(reservation.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>시작</p>
          <p>{reservation && getTimeString(reservation.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>종료</p>
          <p>{reservation && getTimeString(reservation.interviewEndTime)}</p>
        </S.Info>
      </S.InfoContainer>

      <S.ButtonContainer>
        <Button width="47%" height="35px" white={true} onClick={handleOpenModal}>
          면담확인
        </Button>

        <Link to={PAGE.CREW_HOME}>
          <Button width="100%" height="35px">
            홈으로
          </Button>
        </Link>
      </S.ButtonContainer>
      <ReservationDetailModal
        show={show}
        display={display}
        role={memberRole}
        reservationId={Number(reservationId)}
        handleCloseModal={handleCloseModal}
      />
    </S.Box>
  );
};

export default ReservationCompletePage;
