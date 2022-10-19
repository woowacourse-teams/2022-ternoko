import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Navigate, Outlet, useNavigate } from 'react-router-dom';

import ErrorBoundary from '@/Shared/components/ErrorBoundary';
import TernokoLoading from '@/Shared/components/TernokoLoading';

import { useUserState } from '@/Shared/context/UserProvider';

import { validateAccessTokenAPI } from '@/Shared/api';
import { PATH } from '@/Shared/constants/path';
import LocalStorage from '@/Shared/localStorage';

import { MemberExtendedRoleType } from '@/Types/domain';

type PrivateRouteProps = {
  auth: MemberExtendedRoleType;
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
    return <Navigate to={PATH.LOGIN} />;
  }

  const { nickname } = useUserState();

  if (checkNickname && !nickname) {
    return <Navigate to={PATH.LOGIN_REGISTER} />;
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
