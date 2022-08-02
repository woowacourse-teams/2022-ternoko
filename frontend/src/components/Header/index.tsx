import { Link } from 'react-router-dom';

import * as S from './styled';

import { useUserState } from '@/context/UserProvider';

import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const Header = () => {
  const { nickname, imageUrl } = useUserState();
  const memberRole = LocalStorage.getMemberRole();

  return (
    <S.Box>
      {memberRole === 'COACH' ? (
        <Link to={PAGE.COACH_HOME}>
          <img src="/assets/logo/mainLogo.png" alt="로고" />
          <h1>코치도 터놓고</h1>
        </Link>
      ) : (
        <Link to={PAGE.CREW_HOME}>
          <img src="/assets/logo/mainLogo.png" alt="로고" />
          <h1>크루도 터놓고</h1>
        </Link>
      )}
      <S.MenuBox>
        <S.ProfileImage src={imageUrl} alt="프로필" />
        <S.Nickname>{nickname}</S.Nickname>
      </S.MenuBox>
    </S.Box>
  );
};

export default Header;
