import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import GridContainer from '@/components/@common/GridContainer/styled';

import Reservation from '@/components/Reservation';

import { ReservationType } from '@/types/domain';

import { getReservationsAPI } from '@/api';
import { PAGE } from '@/constants';

export type TabMenuStatus = 'doing' | 'done';

const HomePage = () => {
  const [reservations, setReservations] = useState<ReservationType[]>([]);
  const [tabMenuStatus, setTabMenuStatus] = useState<TabMenuStatus>('doing');

  const getHandleClickTabMenu = (status: TabMenuStatus) => () => {
    setTabMenuStatus(status);
  };

  useEffect(() => {
    (async () => {
      const response = await getReservationsAPI();
      setReservations(response.data);
    })();
  }, []);

  return (
    <>
      <S.TitleBox>
        <h2>나의 면담</h2>
        <Link to={PAGE.RESERVATION_APPLY}>
          <Button home>+ 신청하기</Button>
        </Link>
      </S.TitleBox>

      <S.TabMenuBox>
        <S.TabMenu active={tabMenuStatus === 'doing'} onClick={getHandleClickTabMenu('doing')}>
          진행중 면담
        </S.TabMenu>
        <S.TabMenu active={tabMenuStatus === 'done'} onClick={getHandleClickTabMenu('done')}>
          완료한 면담
        </S.TabMenu>
      </S.TabMenuBox>

      <GridContainer minSize="25rem" pt="4rem">
        {reservations.map((reservation) => (
          <Reservation key={reservation.id} {...reservation} />
        ))}
      </GridContainer>
    </>
  );
};

export default HomePage;
