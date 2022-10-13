import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import TernokoLoading from '@/components/@common/TernokoLoading';

import { useUserActions } from '@/context/UserProvider';

import { UserStatusType } from '@/types/domain';

import { getUserStatusAPI, getUserStatusAPICoach } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

export const OAuthRedirectHandlerPageCoach = () => {
  const navigate = useNavigate();
  const { search } = useLocation();
  const { initializeUser } = useUserActions();

  const code = search.match(/(code=).+/)?.[0].replace('code=', '');

  (async () => {
    const response = await getUserStatusAPICoach();

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

// export {OAuthRedirectHandlerPage, OAuthRedirectHandlerPageCoach};
export default OAuthRedirectHandlerPageCoach;
