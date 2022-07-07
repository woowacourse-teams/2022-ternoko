import { Link } from 'react-router-dom';

import * as S from './styled';

const Header = () => {
  return (
    <S.Box>
      <Link to="/">
        <h1>터놓고</h1>
      </Link>
      <S.MenuBox>
        <S.MenuItem>로그인</S.MenuItem>
        <S.MenuItem>회원가입</S.MenuItem>
      </S.MenuBox>
    </S.Box>
  );
};

export default Header;
