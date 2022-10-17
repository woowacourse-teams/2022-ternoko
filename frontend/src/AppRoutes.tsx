import { Suspense, lazy, useEffect } from 'react';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';

import styled from 'styled-components';

import TernokoLoading from '@/common/components/@common/TernokoLoading';
import Toast from '@/common/components/@common/Toast';
import Header from '@/common/components/Header';
import PrivateRoute from '@/common/components/PrivateRoute';
import { PAGE } from '@/common/constants';
import CalendarProvider from '@/common/context/CalendarProvider';
import { useLoadingState } from '@/common/context/LoadingProvider';
import { useUserActions } from '@/common/context/UserProvider';

// 크루 도메인
const HomePage = lazy(() => import('@/Crew/pages/HomePage'));
const InterviewApplyPage = lazy(() => import('@/Crew/pages/InterviewApplyPage'));
const InterviewCompletePage = lazy(() => import('@/Crew/pages/InterviewCompletePage'));

// 코치 도메인
const CoachHomePage = lazy(() => import('@/Coach/pages/CoachHomePage'));
const CoachInterviewCreatePage = lazy(() => import('@/Coach/pages/CoachInterviewCreatePage'));

// 공통 도메인
const LoginPage = lazy(() => import('@/common/pages/LoginPage'));
const LoginRegisterPage = lazy(() => import('@/common/pages/LoginRegisterPage'));
const MyPage = lazy(() => import('@/common/pages/MyPage'));
const NotFoundPage = lazy(() => import('@/common/pages/NotFoundPage'));
const OAuthRedirectHandlerPage = lazy(() => import('@/common/pages/OAuthRedirectHandlerPage'));

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
          <Route path={PAGE.LOGIN} element={<LoginPage />} />
          <Route path={PAGE.OAUTH_REDIRECT} element={<OAuthRedirectHandlerPage />} />
          <Route path={PAGE.NOT_FOUND} element={<NotFoundPage type="DEFAULT" />} />
          <Route path={PAGE.ACCESS_DENY} element={<NotFoundPage type="DENY" />} />

          <Route path={PAGE.BASE} element={<Layout />}>
            <Route element={<PrivateRoute auth="ALL" checkNickname={false} />}>
              <Route path={PAGE.LOGIN_REGISTER} element={<LoginRegisterPage />} />
            </Route>
            <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
              <Route path={PAGE.CREW_HOME} element={<HomePage />} />
            </Route>

            <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
              <Route
                path={`${PAGE.INTERVIEW_COMPLETE}/:interviewId`}
                element={<InterviewCompletePage />}
              />
            </Route>
            <Route element={<PrivateRoute auth="COACH" checkNickname={true} />}>
              <Route
                path={PAGE.COACH_INTERVIEW_CREATE}
                element={
                  <CalendarProvider selectMode="MULTIPLE">
                    <CoachInterviewCreatePage />
                  </CalendarProvider>
                }
              />
            </Route>
            <Route element={<PrivateRoute auth="COACH" checkNickname={true} />}>
              <Route path={PAGE.COACH_HOME} element={<CoachHomePage />} />
            </Route>
            <Route element={<PrivateRoute auth="ALL" checkNickname={true} />}>
              <Route path={PAGE.MY_PAGE} element={<MyPage />} />
            </Route>
          </Route>

          <Route element={<PrivateRoute auth="CREW" checkNickname={true} />}>
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
