import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Navigate, Outlet } from 'react-router-dom';

import ErrorBoundary from '@/components/@common/ErrorBoundary';
import TernokoLoading from '@/components/@common/TernokoLoading';

import { MemberExtendedRole } from '@/types/domain';

import { validateAccessTokenAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

type PrivateRouteProps = {
  auth: MemberExtendedRole;
};

const Pending = ({ auth }: PrivateRouteProps) => {
  useQuery('authorization', () => validateAccessTokenAPI(auth), {
    suspense: true,
    retry: 0,
    staleTime: 0,
  });

  return <Outlet />;
};

const additionalBoxStyle =
  'position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); width: 50%; height: 50%; background-color: unset;';

const PrivateRoute = ({ auth }: PrivateRouteProps) => {
  const accessToken = LocalStorage.getAccessToken();

  if (!accessToken) {
    return <Navigate to={PAGE.LOGIN} />;
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
