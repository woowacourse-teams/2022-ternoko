import { Suspense, lazy, useEffect } from 'react';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';

import styled from 'styled-components';

import PrivateRoute from '@/Router/PrivateRoute';

import Header from '@/Shared/components/Header';
import TernokoLoading from '@/Shared/components/TernokoLoading';
import Toast from '@/Shared/components/Toast';

import CalendarProvider from '@/Shared/context/CalendarProvider';
import { useLoadingState } from '@/Shared/context/LoadingProvider';
import { useUserActions } from '@/Shared/context/UserProvider';

import { PATH } from '@/Shared/constants/path';

const CrewHomePage = lazy(() => import('@/Crew/pages/CrewHomePage'));
const CrewInterviewApplyPage = lazy(() => import('@/Crew/pages/CrewInterviewApplyPage'));
const CrewInterviewCompletePage = lazy(() => import('@/Crew/pages/CrewInterviewCompletePage'));

const CoachHomePage = lazy(() => import('@/Coach/pages/CoachHomePage'));
const CoachInterviewCreatePage = lazy(() => import('@/Coach/pages/CoachInterviewCreatePage'));

const LoginPage = lazy(() => import('@/Shared/pages/LoginPage'));
const LoginRegisterPage = lazy(() => import('@/Shared/pages/LoginRegisterPage'));
const MyPage = lazy(() => import('@/Shared/pages/MyPage'));
const NotFoundPage = lazy(() => import('@/Shared/pages/NotFoundPage'));
const OAuthRedirectHandlerPage = lazy(() => import('@/Shared/pages/OAuthRedirectHandlerPage'));

const AppRoutes = () => {
  const { initializeUser } = useUserActions();
  const { show } = useLoadingState();

  useEffect(() => {
    initializeUser(null);
  }, []);

  return (
    <BrowserRouter>
      <Suspense fallback={<TernokoLoading />}>
        <Routes>
          <Route path={PATH.LOGIN} element={<LoginPage />} />
          <Route path={PATH.OAUTH_REDIRECT} element={<OAuthRedirectHandlerPage />} />
          <Route path={PATH.NOT_FOUND} element={<NotFoundPage type="DEFAULT" />} />
          <Route path={PATH.ACCESS_DENY} element={<NotFoundPage type="DENY" />} />

          <Route path={PATH.BASE} element={<Layout />}>
            <Route element={<PrivateRoute auth="ALL" checkNickname={false} />}>
              <Route path={PATH.LOGIN_REGISTER} element={<LoginRegisterPage />} />
            </Route>
            <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
              <Route path={PATH.CREW_HOME} element={<CrewHomePage />} />
            </Route>

            <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
              <Route
                path={`${PATH.INTERVIEW_COMPLETE}/:interviewId`}
                element={<CrewInterviewCompletePage />}
              />
            </Route>
            <Route element={<PrivateRoute auth="COACH" checkNickname={true} />}>
              <Route
                path={PATH.COACH_INTERVIEW_CREATE}
                element={
                  <CalendarProvider selectMode="MULTIPLE">
                    <CoachInterviewCreatePage />
                  </CalendarProvider>
                }
              />
            </Route>
            <Route element={<PrivateRoute auth="COACH" checkNickname={true} />}>
              <Route path={PATH.COACH_HOME} element={<CoachHomePage />} />
            </Route>
            <Route element={<PrivateRoute auth="ALL" checkNickname={true} />}>
              <Route path={PATH.MY_PAGE} element={<MyPage />} />
            </Route>
          </Route>

          <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
            <Route
              path={PATH.INTERVIEW_APPLY}
              element={
                <CalendarProvider selectMode="SINGLE">
                  <CrewInterviewApplyPage />
                </CalendarProvider>
              }
            />
          </Route>
        </Routes>
      </Suspense>
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

    @media ${({ theme }) => theme.devices.laptop(50)} {
      padding: 3rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.tablet()} {
      padding: 2rem 5rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileL(30)} {
      padding: 2rem 2rem 0;
    }

    @media ${({ theme }) => theme.devices.mobileM()} {
      padding: 2rem 1rem 0;
    }
  `,
};

export default AppRoutes;
