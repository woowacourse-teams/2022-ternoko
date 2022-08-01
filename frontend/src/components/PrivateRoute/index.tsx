import { Navigate, Outlet } from 'react-router-dom';

import { PAGE } from '@/constants';

const PrivateRoute = () => {
  return localStorage.getItem('accessToken') ? <Outlet /> : <Navigate to={PAGE.LOGIN} />;
};

export default PrivateRoute;
