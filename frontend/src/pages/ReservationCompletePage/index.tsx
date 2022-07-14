import { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';

import * as S from './styled';

import Button from '../../components/@common/Button/styled';

import { ReservationType } from 'types/domain';
import { getReservationAPI } from '../../api';
import { getDateString, getTimeString } from '../../utils';

const ReservationCompletePage = () => {
  const { reservationId } = useParams();
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
        <S.Logo src={reservation?.imageUrl} alt="로고" />
        <h2>{reservation?.crewNickname}님~ 면담 신청이 완료되었습니다.</h2>
      </S.LogoBox>

      <S.InfoContainer>
        <S.Info>
          <p>코치</p>
          <p>{reservation?.coachNickname}</p>
        </S.Info>
        <S.Info>
          <p>날짜</p>
          <p>{reservation && getDateString(reservation?.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>시작</p>
          <p>{reservation && getTimeString(reservation?.interviewStartTime)}</p>
        </S.Info>
        <S.Info>
          <p>종료</p>
          <p>{reservation && getTimeString(reservation?.interviewEndTime)}</p>
        </S.Info>
      </S.InfoContainer>

      <S.ButtonContainer>
        <Link to="/">
          <Button width="100%" height="35px" white={true}>
            면담확인
          </Button>
        </Link>
        <Link to="/">
          <Button width="100%" height="35px">
            홈으로
          </Button>
        </Link>
      </S.ButtonContainer>
    </S.Box>
  );
};

export default ReservationCompletePage;
