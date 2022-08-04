import { useEffect, useState } from 'react';

import * as S from './styled';

import { PAGE } from '@/constants';

export type TernokoMemberNickname = '앤지' | '애쉬' | '열음' | '바니' | '수달' | '아놀드' | '록바';
export type TernokoMemberStatus = 'BORN' | 'ALIVE';

type TernokoMember = {
  nickname: TernokoMemberNickname;
  imageUrl: string;
  status: TernokoMemberStatus;
};

const ternokoMembers = [
  {
    nickname: '앤지',
    imageUrl: '/assets/image/angel.png',
    status: 'BORN',
  },
  {
    nickname: '애쉬',
    imageUrl: '/assets/image/ash.png',
    status: 'BORN',
  },
  {
    nickname: '열음',
    imageUrl: '/assets/image/yeoleum.png',
    status: 'BORN',
  },
  {
    nickname: '바니',
    imageUrl: '/assets/image/bunny.png',
    status: 'BORN',
  },
  {
    nickname: '수달',
    imageUrl: '/assets/image/sudal.png',
    status: 'BORN',
  },
  {
    nickname: '아놀드',
    imageUrl: '/assets/image/arnold.png',
    status: 'BORN',
  },
  {
    nickname: '록바',
    imageUrl: '/assets/image/lokba.png',
    status: 'BORN',
  },
] as TernokoMember[];

const LoginPage = () => {
  const [members, setMembers] = useState<TernokoMember[]>(ternokoMembers);

  useEffect(() => {
    setMembers((prev) => prev.map((member) => ({ ...member, status: 'ALIVE' })));
  }, []);

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
        {members.map(({ nickname, imageUrl, status }) => (
          <S.TernokoProfile key={nickname} status={status} nickname={nickname}>
            <img src={imageUrl} alt="터놓고 프로필" />
          </S.TernokoProfile>
        ))}
      </S.RightBox>
    </S.Box>
  );
};
export default LoginPage;
