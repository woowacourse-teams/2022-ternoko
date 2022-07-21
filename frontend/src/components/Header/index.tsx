import { Link, useLocation } from 'react-router-dom';

import * as S from './styled';

const logoURL =
  'https://user-images.githubusercontent.com/19251499/180158202-4d757b47-d804-45e4-b73b-9d6e13fe844b.png';

const Header = () => {
  const location = useLocation();

  return (
    <S.Box>
      {location.pathname.includes('coach') ? (
        <Link to="/coach/home">
          <img src={logoURL} />
          <h1>코치도 터놓고</h1>
        </Link>
      ) : (
        <Link to="/">
          <img src={logoURL} />
          <h1>크루도 터놓고</h1>
        </Link>
      )}
      <S.MenuBox>
        <S.MenuItem>로그인</S.MenuItem>
        <S.MenuItem>회원가입</S.MenuItem>
      </S.MenuBox>
    </S.Box>
  );
};

export default Header;
