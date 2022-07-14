import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';

import styled from 'styled-components';

import CoachHomePage from '@/pages/CoachHomePage';
import CoachReservationCreatePage from '@/pages/CoachReservationCreatePage';
import HomePage from '@/pages/HomePage';
import LoginPage from '@/pages/LoginPage';
import LoginRegisterPage from '@/pages/LoginRegisterPage';
import MyPage from '@/pages/MyPage';
import OAuthRedirectHandlerPage from '@/pages/OAuthRedirectHandlerPage';
import ReservationApplyPage from '@/pages/ReservationApplyPage';
import ReservationCompletePage from '@/pages/ReservationCompletePage';

import Toast from '@/components/@common/Toast';

import Header from '@/components/Header';
import PrivateRoute from '@/components/PrivateRoute';

import CalendarProvider from '@/context/CalendarProvider';
import { useUserActions } from '@/context/UserProvider';

import { PAGE } from '@/constants';

const AppRoutes = () => {
  const { initializeUser } = useUserActions();

  initializeUser(null);

  return (
    <BrowserRouter>
      <Routes>
        <Route path={PAGE.LOGIN} element={<LoginPage />} />
        <Route path={PAGE.OAUTH_REDIRECT} element={<OAuthRedirectHandlerPage />} />

        <Route path="/" element={<Layout />}>
          <Route element={<PrivateRoute />}>
            <Route path={PAGE.LOGIN_REGISTER} element={<LoginRegisterPage />} />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route path={PAGE.CREW_HOME} element={<HomePage />} />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route
              path={PAGE.RESERVATION_APPLY}
              element={
                <CalendarProvider selectMode="single">
                  <ReservationApplyPage />
                </CalendarProvider>
              }
            />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route
              path={`${PAGE.RESERVATION_COMPLETE}/:reservationId`}
              element={<ReservationCompletePage />}
            />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route
              path={PAGE.COACH_RESERVATION_CREATE}
              element={
                <CalendarProvider selectMode="multiple">
                  <CoachReservationCreatePage />
                </CalendarProvider>
              }
            />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route path={PAGE.COACH_HOME} element={<CoachHomePage />} />
          </Route>
          <Route element={<PrivateRoute />}>
            <Route path={PAGE.MY_PAGE} element={<MyPage />} />
          </Route>
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
        <Toast />
      </S.Body>
    </>
  );
};

const S = {
  Body: styled.div`
    min-height: calc(100% - 60px);
    padding: 3rem 30rem 0;

    @media ${({ theme }) => theme.devices.laptopL} {
      padding: 3rem 25rem 0;
    }

    @media ${({ theme }) => theme.devices.laptop} {
      padding: 3rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.tablet} {
      padding: 2rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileL} {
      padding: 2rem 2rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileM} {
      padding: 2rem 1rem 0;
    }
  `,
};

export default AppRoutes;

// 카밋 테스트
