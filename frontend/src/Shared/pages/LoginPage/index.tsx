import * as S from './styled';

import Loading from '@/Shared/components/Loading';

import { PATH } from '@/Shared/constants/path';

const LoginPage = () => {
  return (
    <S.Box>
      <S.LeftBox>
        <div>
          <h3>면담은 찐하게, 예약은 손쉽게</h3>
          <h2>면담 예약 서비스</h2>
          <S.LogoBox>
            <h1>터놓고</h1>
            <picture>
              <source srcSet="/assets/logo/mainLogo.avif" type="image/avif" />
              <img src="/assets/logo/mainLogo.png" alt="로고" />
            </picture>
          </S.LogoBox>
          <a href={PATH.SLACK_LOGIN_SERVER}>
            <S.LoginButton>
              <picture>
                <source srcSet="/assets/icon/slack.avif" type="image/avif" />
                <img src="/assets/icon/slack.png" alt="슬랙 로고" />
              </picture>
              슬랙 로그인
            </S.LoginButton>
          </a>
        </div>
      </S.LeftBox>
      <Loading profileSizeRem={25} animationDuration={5} />
    </S.Box>
  );
};
export default LoginPage;
