import * as S from './styled';

import { CoachType } from '@/Types/domain';

export type ProfileProps = CoachType & {
  currentCoachId: number;
  hasOpenTime: boolean;
  getHandleClickProfile: (id: number) => () => void;
};

const Profile = ({
  id,
  currentCoachId,
  nickname,
  imageUrl,
  hasOpenTime,
  getHandleClickProfile,
}: ProfileProps) => {
  return (
    <S.Box
      active={currentCoachId === id}
      hasOpenTime={hasOpenTime}
      onClick={getHandleClickProfile(id)}
    >
      <S.ProfileImage src={imageUrl} alt="코치 프로필" />
      <S.CoachName>{nickname}</S.CoachName>
    </S.Box>
  );
};

export default Profile;
