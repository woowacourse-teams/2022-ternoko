import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import { getUserStatusAPI } from '@/common/api';
import TernokoLoading from '@/common/components/@common/TernokoLoading';
import { PAGE } from '@/common/constants';
import { useUserActions } from '@/common/context/UserProvider';
import LocalStorage from '@/common/localStorage';
import { UserStatusType } from '@/common/types/domain';

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
