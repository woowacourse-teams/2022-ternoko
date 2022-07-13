import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';

import HomePage from './pages/HomePage';
import ReservationApplyPage from './pages/ReservationApplyPage';
import ReservationCompletePage from './pages/ReservationCompletePage';

import Header from './components/Header';
import CalendarProvider from './context/CalendarProvider';

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Header />

      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="" element={<HomePage />} />
          <Route
            path="reservation/apply"
            element={
              <CalendarProvider>
                <ReservationApplyPage />
              </CalendarProvider>
            }
          />
          <Route path="reservation/complete/:reservationId" element={<ReservationCompletePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

const Layout = () => {
  return (
    <S.Body>
      <Outlet />
    </S.Body>
  );
};

const S = {
  Body: styled.div`
    min-height: calc(100% - 60px);
    padding: 3rem 30rem 0;
  `,
};

export default AppRoutes;
