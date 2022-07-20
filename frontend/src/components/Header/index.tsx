import { Link, useLocation } from 'react-router-dom';

import * as S from './styled';

const logoURL =
  'https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F6b9fa6f5-4448-4033-97da-1c425b75bd35%2FIMG_92500CCD3DA4-1_2.jpeg?table=block&id=b24abb05-6f3f-4261-a429-50952525f7fb&spaceId=69b38b44-10b7-40af-9192-e032c5471fd2&width=2000&userId=c6e4abc8-389f-4426-a98c-49603c6569cb&cache=v2';

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
