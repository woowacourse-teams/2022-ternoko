import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Navigate, Outlet, useNavigate } from 'react-router-dom';

import ErrorBoundary from '@/components/@common/ErrorBoundary';
import TernokoLoading from '@/components/@common/TernokoLoading';

import { useUserState } from '@/context/UserProvider';

import { MemberExtendedRole } from '@/types/domain';

import { validateAccessTokenAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

type PrivateRouteProps = {
  auth: MemberExtendedRole;
  checkNickname: boolean;
};

type PendingProps = Pick<PrivateRouteProps, 'auth'>;

const Pending = ({ auth }: PendingProps) => {
  useQuery(`authorization-${auth}`, () => validateAccessTokenAPI(auth), {
    suspense: true,
    retry: 0,
    staleTime: 0,
  });

  return <Outlet />;
};

const PrivateRoute = ({ auth, checkNickname }: PrivateRouteProps) => {
  const accessToken = LocalStorage.getAccessToken();

  if (!accessToken) {
    return <Navigate to={PAGE.LOGIN} />;
  }

  const { nickname } = useUserState();

  if (checkNickname && !nickname) {
    return <Navigate to={PAGE.LOGIN_REGISTER} />;
  }

  const navigate = useNavigate();

  if (!checkNickname && nickname) {
    navigate(-1);
  }

  return (
    <ErrorBoundary>
      <Suspense fallback={<TernokoLoading />}>
        <Pending auth={auth} />
      </Suspense>
    </ErrorBoundary>
  );
};

export default PrivateRoute;
