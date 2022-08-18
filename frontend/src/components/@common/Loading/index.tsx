import * as S from './styled';

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

type LoadingProps = {
  additionalBoxStyle?: string;
  profileSizeRem: number;
  animationDuration: number;
};

const Loading = ({ additionalBoxStyle, profileSizeRem, animationDuration }: LoadingProps) => {
  return (
    <S.Box additionalBoxStyle={additionalBoxStyle}>
      {ternokoMembers.map(({ nickname, imageUrl }) => (
        <S.TernokoProfile
          key={nickname}
          profileSizeRem={profileSizeRem}
          animationDuration={animationDuration}
          nickname={nickname}
        >
          <img src={imageUrl} alt="터놓고 프로필" />
        </S.TernokoProfile>
      ))}
    </S.Box>
  );
};

export default Loading;
