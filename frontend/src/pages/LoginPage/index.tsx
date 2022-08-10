import * as S from './styled';

import { PAGE } from '@/constants';

export type TernokoMemberNickname = '앤지' | '애쉬' | '열음' | '바니' | '수달' | '아놀드' | '록바';

type TernokoMember = {
  nickname: TernokoMemberNickname;
  imageUrl: string;
};

const ternokoMembers = [
  {
    nickname: '앤지',
    imageUrl: '/assets/image/angel.png',
  },
  {
    nickname: '애쉬',
    imageUrl: '/assets/image/ash.png',
  },
  {
    nickname: '열음',
    imageUrl: '/assets/image/yeoleum.png',
  },
  {
    nickname: '바니',
    imageUrl: '/assets/image/bunny.png',
  },
  {
    nickname: '수달',
    imageUrl: '/assets/image/sudal.png',
  },
  {
    nickname: '아놀드',
    imageUrl: '/assets/image/arnold.png',
  },
  {
    nickname: '록바',
    imageUrl: '/assets/image/lokba.png',
  },
] as TernokoMember[];

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
      <S.RightBox>
        {ternokoMembers.map(({ nickname, imageUrl }) => (
          <S.TernokoProfile key={nickname} nickname={nickname}>
            <img src={imageUrl} alt="터놓고 프로필" />
          </S.TernokoProfile>
        ))}
      </S.RightBox>
    </S.Box>
  );
};
export default LoginPage;
