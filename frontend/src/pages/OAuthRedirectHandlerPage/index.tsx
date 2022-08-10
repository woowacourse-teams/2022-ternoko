import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import { useUserActions } from '@/context/UserProvider';

import { UserStatusType } from '@/types/domain';

import { getUserStatusAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const OAuthRedirectHandlerPage = () => {
  const navigate = useNavigate();
  const { search } = useLocation();
  const { initializeUser } = useUserActions();

  const code = search.match(/(?<=code=).+/)?.[0];

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

  return <p>로딩중....</p>;
};

export default OAuthRedirectHandlerPage;
