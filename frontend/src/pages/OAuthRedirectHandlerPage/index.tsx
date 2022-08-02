import axios from 'axios';

import { useLocation, useNavigate } from 'react-router-dom';

import { UserStatusType } from '@/types/domain';

import { getUserStatusAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const OAuthRedirectHandlerPage = () => {
  const navigate = useNavigate();
  const { search } = useLocation();

  const code = search.match(/(?<=code=).+/)?.[0];

  (async () => {
    const response = await getUserStatusAPI(code as string);
    const { accessToken, hasNickname, memberRole }: UserStatusType = response.data;

    LocalStorage.setAccessToken(accessToken);
    LocalStorage.setMemberRole(memberRole);
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    if (memberRole === 'CREW') {
      navigate(hasNickname ? PAGE.CREW_HOME : PAGE.LOGIN_REGISTER);
    } else {
      navigate(hasNickname ? PAGE.COACH_HOME : `${PAGE.LOGIN_REGISTER}/?role='coach`);
    }
  })();

  return <p>로딩중....</p>;
};

export default OAuthRedirectHandlerPage;
