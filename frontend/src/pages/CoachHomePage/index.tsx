import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import useModal from '@/components/@common/Modal/useModal';
import ReservationDetailModal from '@/components/@common/ReservationDetailModal';

import CoachCalendar from '@/components/CoachCalendar';

import CalendarProvider from '@/context/CalendarProvider';

import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const CoachHomePage = () => {
  const memberRole = LocalStorage.getMemberRole();
  const { show, display, handleOpenModal, handleCloseModal } = useModal();
  const [clickedReservationId, setClickedReservationId] = useState(-1);
  const calendarRerenderkeyRef = useRef(Date.now());

  const getHandleClickSchedule = (id: number) => () => {
    setClickedReservationId(id);
    handleOpenModal();
  };

  const afterDeleteReservation = () => {
    calendarRerenderkeyRef.current = Date.now();
    handleCloseModal();
  };

  return (
    <>
      <S.TitleBox>
        <h2>면담 리스트</h2>
        <Link to={PAGE.COACH_RESERVATION_CREATE}>
          <Button home>스케쥴 생성</Button>
        </Link>
      </S.TitleBox>
      <CalendarProvider selectMode="single">
        <CoachCalendar
          key={calendarRerenderkeyRef.current}
          getHandleClickSchedule={getHandleClickSchedule}
        />
      </CalendarProvider>
      <ReservationDetailModal
        show={show}
        display={display}
        role={memberRole}
        reservationId={clickedReservationId}
        afterDeleteReservation={afterDeleteReservation}
        handleCloseModal={handleCloseModal}
      />
    </>
  );
};

export default CoachHomePage;
