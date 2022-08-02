import * as S from './styled';

import { PAGE } from '@/constants';

const LoginPage = () => {
  return (
    <S.Box>
      <S.LeftBox>
        <div>
          <h3>면담은 찐하게, 예약은 손쉽게</h3>
          <h2>면담 예약 서비스</h2>
          <S.LogoBox>
            <h1>터놓고</h1>
            <img src="/assets/logo/mainLogo.png" alt="프로필 로고" />
          </S.LogoBox>
          <a href={PAGE.SLACK_LOGIN_SERVER}>
            <S.LoginButton>
              <img src="/assets/icon/slack.png" alt="슬랙 로고" />
              슬랙 로그인
            </S.LoginButton>
          </a>
        </div>
      </S.LeftBox>
      <S.RightBox></S.RightBox>
    </S.Box>
  );
};
export default LoginPage;
