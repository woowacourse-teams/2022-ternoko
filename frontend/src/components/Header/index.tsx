import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import * as S from './styled';

import { useUserState } from '@/context/UserProvider';

import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const Header = () => {
  const navigate = useNavigate();
  const { nickname, imageUrl } = useUserState();
  const [isOpenDropdown, setIsOpenDropdown] = useState(false);

  const memberRole = LocalStorage.getMemberRole();

  const toggleDropdown = () => setIsOpenDropdown((prev) => !prev);

  const handleLogout = () => {
    LocalStorage.removeAccessToken();
    navigate(PAGE.LOGIN);
  };

  return (
    <S.Box>
      {memberRole === 'COACH' ? (
        <Link to={PAGE.COACH_HOME}>
          <picture>
            <source srcSet="/assets/logo/mainLogo.avif" type="image/avif" />
            <img src="/assets/logo/mainLogo.png" alt="로고" />
          </picture>
          <h1>코치도 터놓고</h1>
        </Link>
      ) : (
        <Link to={PAGE.CREW_HOME}>
          <picture>
            <source srcSet="/assets/logo/mainLogo.avif" type="image/avif" />
            <img src="/assets/logo/mainLogo.png" alt="로고" />
          </picture>
          <h1>크루도 터놓고</h1>
        </Link>
      )}
      {nickname && nickname.length && (
        <S.MenuBox>
          <S.Nickname>{nickname}님, 환영합니다 😎</S.Nickname>
          <S.ProfileImage src={imageUrl} alt="프로필" onClick={toggleDropdown} />

          <S.DropdownContainer open={isOpenDropdown}>
            <Link to={PAGE.MY_PAGE}>
              <S.DropdownItem onClick={toggleDropdown}>마이 페이지</S.DropdownItem>
            </Link>
            <S.DropdownItem onClick={handleLogout}>로그아웃</S.DropdownItem>
          </S.DropdownContainer>
        </S.MenuBox>
      )}
      {isOpenDropdown && <S.Dimmer onClick={toggleDropdown} />}
    </S.Box>
  );
};

export default Header;
