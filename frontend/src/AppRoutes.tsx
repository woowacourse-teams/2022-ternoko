import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';

import styled from 'styled-components';

import CoachHomePage from '@/pages/CoachHomePage';
import CoachReservationCreatePage from '@/pages/CoachReservationCreatePage';
import HomePage from '@/pages/HomePage';
import LoginPage from '@/pages/LoginPage';
import LoginRegisterPage from '@/pages/LoginRegisterPage';
import MyPage from '@/pages/MyPage';
import ReservationApplyPage from '@/pages/ReservationApplyPage';
import ReservationCompletePage from '@/pages/ReservationCompletePage';

import Header from '@/components/Header';

import CalendarProvider from '@/context/CalendarProvider';

import { PAGE } from '@/constants';

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={PAGE.LOGIN} element={<LoginPage />} />
        <Route path="/" element={<Layout />}>
          <Route path={PAGE.LOGIN_REGISTER} element={<LoginRegisterPage />} />
          <Route path={PAGE.CREW_HOME} element={<HomePage />} />
          <Route
            path={PAGE.RESERVATION_APPLY}
            element={
              <CalendarProvider selectMode="single">
                <ReservationApplyPage />
              </CalendarProvider>
            }
          />
          <Route
            path={`${PAGE.RESERVATION_COMPLETE}reservationId`}
            element={<ReservationCompletePage />}
          />
          <Route
            path={PAGE.COACH_RESERVATION_CREATE}
            element={
              <CalendarProvider selectMode="multiple">
                <CoachReservationCreatePage />
              </CalendarProvider>
            }
          />
          <Route path={PAGE.COACH_HOME} element={<CoachHomePage />} />
          <Route path={PAGE.MY_PAGE} element={<MyPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

const Layout = () => {
  return (
    <>
      <Header />
      <S.Body>
        <Outlet />
      </S.Body>
    </>
  );
};

const S = {
  Body: styled.div`
    min-height: calc(100% - 60px);
    padding: 3rem 30rem 0;
  `,
};

export default AppRoutes;
