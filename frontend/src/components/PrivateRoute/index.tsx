import { Suspense } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

import Loading from '@/components/@common/Loading';

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

const fetchAccessResult = (role: MemberExtendedRole) => {
  let result: boolean | null = null;

  const suspender = validateAccessTokenAPI(role)
    .then(() => {
      result = true;
    })
    .catch(() => {
      result = false;
    });

  return {
    read() {
      if (result === null) {
        throw suspender;
      }

      return result;
    },
  };
};

const Pending = ({ resource }: PendingProps) => {
  const result = resource.read();

  return result ? <Outlet /> : <Navigate to={PAGE.ACCESS_DENY} />;
};

const additionalBoxStyle =
  'position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); width: 50%; height: 50%; background-color: unset;';

const PrivateRoute = ({ auth }: PrivateRouteProps) => {
  const accessToken = LocalStorage.getAccessToken();

  if (!accessToken) {
    return <Navigate to={PAGE.LOGIN} />;
  }

  return (
    <Suspense
      fallback={
        <Loading
          additionalBoxStyle={additionalBoxStyle}
          profileSizeRem={25}
          animationDuration={1.2}
        />
      }
    >
      <Pending resource={fetchAccessResult(auth)} />
    </Suspense>
  );
};

export default PrivateRoute;
