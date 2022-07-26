import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';

import LoginPage from './pages/LoginPage';
import LoginRegisterPage from './pages/LoginRegisterPage';
import HomePage from './pages/HomePage';
import ReservationApplyPage from './pages/ReservationApplyPage';
import ReservationCompletePage from './pages/ReservationCompletePage';
import CoachReservationCreatePage from './pages/CoachReservationCreatePage';
import CoachHomePage from './pages/CoachHomePage';

import Header from './components/Header';

import CalendarProvider from './context/CalendarProvider';

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<Layout />}>
          <Route path="/login/register" element={<LoginRegisterPage />} />
          <Route path="" element={<HomePage />} />
          <Route
            path="reservation/apply"
            element={
              <CalendarProvider selectMode="single">
                <ReservationApplyPage />
              </CalendarProvider>
            }
          />
          <Route path="reservation/complete/:reservationId" element={<ReservationCompletePage />} />
          <Route
            path="coach/reservation/create"
            element={
              <CalendarProvider selectMode="multiple">
                <CoachReservationCreatePage />
              </CalendarProvider>
            }
          />
          <Route path="coach/home" element={<CoachHomePage />} />
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
