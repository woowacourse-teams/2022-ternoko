import * as S from './styled';

export type TernokoMemberNickname = '앤지' | '애쉬' | '열음' | '바니' | '수달' | '아놀드' | '록바';

type TernokoMember = {
  nickname: TernokoMemberNickname;
  pngImageUrl: string;
  avifImageUrl: string;
};

const ternokoMembers = [
  {
    nickname: '앤지',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/angel.avif',
  },
  {
    nickname: '애쉬',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/ash.avif',
  },
  {
    nickname: '열음',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/yeoleum.avif',
  },
  {
    nickname: '바니',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/bunny.avif',
  },
  {
    nickname: '수달',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/sudal.avif',
  },
  {
    nickname: '아놀드',
    pngImageUrl: '/assets/image/arnold.png',
    avifImageUrl: '/assets/image/angel.avif',
  },
  {
    nickname: '록바',
    pngImageUrl: '/assets/image/angel.png',
    avifImageUrl: '/assets/image/lokba.avif',
  },
] as TernokoMember[];

type LoadingProps = {
  additionalBoxStyle?: string;
  profileSizeRem: number;
  animationDuration: number;
};

const Loading = ({ additionalBoxStyle, profileSizeRem, animationDuration }: LoadingProps) => {
  return (
    <S.Box additionalBoxStyle={additionalBoxStyle}>
      {ternokoMembers.map(({ nickname, pngImageUrl, avifImageUrl }) => (
        <S.TernokoProfile
          key={nickname}
          profileSizeRem={profileSizeRem}
          animationDuration={animationDuration}
          nickname={nickname}
        >
          <picture>
            <source srcSet={avifImageUrl} />
            <img src={pngImageUrl} alt="터놓고 프로필" />
          </picture>
        </S.TernokoProfile>
      ))}
    </S.Box>
  );
};

export default Loading;
