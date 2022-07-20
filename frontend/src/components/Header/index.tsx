import { Link, useLocation } from 'react-router-dom';

import * as S from './styled';

const Header = () => {
  const location = useLocation();

  return (
    <S.Box>
      {location.pathname.includes('coach') ? (
        <Link to="/coach/home">
          <h1>코치도 터놓고</h1>
        </Link>
      ) : (
        <Link to="/">
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
