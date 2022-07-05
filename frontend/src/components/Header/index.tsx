import * as S from './styled';

const Header = () => {
  return (
    <S.Box>
      <S.Logo>터놓고</S.Logo>
      <S.Menu>
        <S.MenuItem>로그인</S.MenuItem>
        <S.MenuItem>회원가입</S.MenuItem>
      </S.Menu>
    </S.Box>
  );
};

export default Header;
