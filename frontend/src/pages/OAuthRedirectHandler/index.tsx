import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import { UserStatusType } from '@/types/domain';

import { getUserStatusAPI } from '@/api';
import { PAGE } from '@/constants';

const OAuthRedirectHandler = () => {
  const navigate = useNavigate();
  const { search } = useLocation();

  const code = search.match(/(?<=code=).+/)?.[0];

  (async () => {
    const response = await getUserStatusAPI(code as string);
    const { accessToken, hasNickname, memberRole }: UserStatusType = response.data;

    localStorage.setItem('accessToken', accessToken);
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    if (memberRole === 'CREW') {
      navigate(hasNickname ? PAGE.CREW_HOME : PAGE.LOGIN_REGISTER);
    } else {
      navigate(hasNickname ? PAGE.COACH_HOME : `${PAGE.LOGIN_REGISTER}/?role='coach`);
    }
  })();

  return <p></p>;
};

export default OAuthRedirectHandler;
