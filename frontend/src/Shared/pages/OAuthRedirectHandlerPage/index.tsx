import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import TernokoLoading from '@/Shared/components/TernokoLoading';

import { useUserActions } from '@/Shared/context/UserProvider';

import { getUserStatusAPI } from '@/Shared/api';
import { PATH } from '@/Shared/constants/path';
import LocalStorage from '@/Shared/localStorage';

const OAuthRedirectHandlerPage = () => {
  const navigate = useNavigate();
  const { search } = useLocation();
  const { initializeUser } = useUserActions();

  const code = search.match(/(code=).+/)?.[0].replace('code=', '');

  (async () => {
    const response = await getUserStatusAPI(
      code as string,
      process.env.SLACK_REDIRECT_URL as string,
    );

    const { accessToken, hasNickname, memberRole } = response.data;

    LocalStorage.setAccessToken(accessToken);
    LocalStorage.setMemberRole(memberRole);
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    initializeUser(() => {
      if (memberRole === 'CREW') {
        navigate(hasNickname ? PATH.CREW_HOME : PATH.LOGIN_REGISTER);
      } else {
        navigate(hasNickname ? PATH.COACH_HOME : PATH.LOGIN_REGISTER);
      }
    });
  })();

  return <TernokoLoading />;
};

export default OAuthRedirectHandlerPage;
