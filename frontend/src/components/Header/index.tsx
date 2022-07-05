import * as S from './styled';

const Header = () => {
  return (
    <S.Box>
      <S.LogoBox>
        <h1>터놓고</h1>
      </S.LogoBox>
      <S.MenuBox>
        <S.MenuItem>로그인</S.MenuItem>
        <S.MenuItem>회원가입</S.MenuItem>
      </S.MenuBox>
    </S.Box>
  );
};

export default Header;
