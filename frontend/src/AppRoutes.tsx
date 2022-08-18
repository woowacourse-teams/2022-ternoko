import { useEffect } from 'react';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';

import styled from 'styled-components';

import CoachHomePage from '@/pages/CoachHomePage';
import CoachInterviewCreatePage from '@/pages/CoachInterviewCreatePage';
import HomePage from '@/pages/HomePage';
import InterviewApplyPage from '@/pages/InterviewApplyPage';
import InterviewCompletePage from '@/pages/InterviewCompletePage';
import LoginPage from '@/pages/LoginPage';
import LoginRegisterPage from '@/pages/LoginRegisterPage';
import MyPage from '@/pages/MyPage';
import NotFoundPage from '@/pages/NotFoundPage';
import OAuthRedirectHandlerPage from '@/pages/OAuthRedirectHandlerPage';

import TernokoLoading from '@/components/@common/TernokoLoading';
import Toast from '@/components/@common/Toast';

import Header from '@/components/Header';
import PrivateRoute from '@/components/PrivateRoute';

import CalendarProvider from '@/context/CalendarProvider';
import { useLoadingState } from '@/context/LoadingProvider';
import { useUserActions } from '@/context/UserProvider';

import { PAGE } from '@/constants';

const AppRoutes = () => {
  const { initializeUser } = useUserActions();
  const { show } = useLoadingState();

  useEffect(() => {
    initializeUser(null);
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path={PAGE.LOGIN} element={<LoginPage />} />
        <Route path={PAGE.OAUTH_REDIRECT} element={<OAuthRedirectHandlerPage />} />
        <Route path={PAGE.ACCESS_DENY} element={<NotFoundPage type="DENY" />} />
        <Route path={PAGE.NOT_FOUND} element={<NotFoundPage type="DEFAULT" />} />

        <Route path={PAGE.BASE} element={<Layout />}>
          <Route element={<PrivateRoute auth="ALL" />}>
            <Route path={PAGE.LOGIN_REGISTER} element={<LoginRegisterPage />} />
          </Route>
          <Route element={<PrivateRoute auth="CREW" />}>
            <Route path={PAGE.CREW_HOME} element={<HomePage />} />
          </Route>

          <Route element={<PrivateRoute auth="CREW" />}>
            <Route
              path={`${PAGE.INTERVIEW_COMPLETE}/:interviewId`}
              element={<InterviewCompletePage />}
            />
          </Route>
          <Route element={<PrivateRoute auth="COACH" />}>
            <Route
              path={PAGE.COACH_INTERVIEW_CREATE}
              element={
                <CalendarProvider selectMode="MULTIPLE">
                  <CoachInterviewCreatePage />
                </CalendarProvider>
              }
            />
          </Route>
          <Route element={<PrivateRoute auth="COACH" />}>
            <Route path={PAGE.COACH_HOME} element={<CoachHomePage />} />
          </Route>
          <Route element={<PrivateRoute auth="ALL" />}>
            <Route path={PAGE.MY_PAGE} element={<MyPage />} />
          </Route>
        </Route>

        <Route element={<PrivateRoute auth="CREW" />}>
          <Route
            path={PAGE.INTERVIEW_APPLY}
            element={
              <CalendarProvider selectMode="SINGLE">
                <InterviewApplyPage />
              </CalendarProvider>
            }
          />
        </Route>
      </Routes>
      <Toast />
      {show && <TernokoLoading />}
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
    position: relative;
    min-height: calc(100% - 60px);
    padding: 3rem 30rem 0;

    @media ${({ theme }) => theme.devices.laptopL()} {
      padding: 3rem 25rem 0;
    }

    @media ${({ theme }) => theme.devices.laptop()} {
      padding: 3rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.tablet()} {
      padding: 2rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileL()} {
      padding: 2rem 2rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileM()} {
      padding: 2rem 1rem 0;
    }
  `,
};

export default AppRoutes;
