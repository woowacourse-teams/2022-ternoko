import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import TernokoLoading from '@/Shared/components/TernokoLoading';

import { useUserActions } from '@/Shared/context/UserProvider';

import { getUserStatusAPI } from '@/Shared/api';
import { PAGE } from '@/Shared/constants';
import LocalStorage from '@/Shared/localStorage';

import { UserStatusType } from '@/Types/domain';

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
    const { accessToken, hasNickname, memberRole }: UserStatusType = response.data;

    LocalStorage.setAccessToken(accessToken);
    LocalStorage.setMemberRole(memberRole);
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    initializeUser(() => {
      if (memberRole === 'CREW') {
        navigate(hasNickname ? PAGE.CREW_HOME : PAGE.LOGIN_REGISTER);
      } else {
        navigate(hasNickname ? PAGE.COACH_HOME : PAGE.LOGIN_REGISTER);
      }
    });
  })();

  return <TernokoLoading />;
};

export default OAuthRedirectHandlerPage;
