import { Suspense } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

import { MemberExtendedRole } from '@/types/domain';

import { validateAccessTokenAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

type PrivateRouteProps = {
  auth: MemberExtendedRole;
};

type PendingProps = {
  resource: { read: () => Promise<void> | boolean };
};

const Pending = ({ resource }: PendingProps) => {
  const result = resource.read();

  return result ? <Outlet /> : <Navigate to={PAGE.ACCESS_DENY} />;
};

const fetchAccessResult = (role: MemberExtendedRole) => {
  let result: boolean | null = null;

  const suspender = validateAccessTokenAPI(role).then((response) => {
    result = response.data;
  });

  return {
    read() {
      if (result === null) {
        return suspender;
      }
      return result;
    },
  };
};

const PrivateRoute = ({ auth }: PrivateRouteProps) => {
  const accessToken = LocalStorage.getAccessToken();

  if (!accessToken) {
    return <Navigate to={PAGE.LOGIN} />;
  }

  return (
    <Suspense fallback={<p>loading...</p>}>
      <Pending resource={fetchAccessResult(auth)} />
    </Suspense>
  );
};

export default PrivateRoute;
