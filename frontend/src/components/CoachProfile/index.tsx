import * as S from './styled';

import { CoachType } from 'types/domain';

export type CoachProfileProps = CoachType & {
  currentCoachId: number;
  getHandleClickProfile: (id: number) => () => void;
};

const CoachProfile = ({
  id,
  currentCoachId,
  nickname,
  imageUrl,
  getHandleClickProfile,
}: CoachProfileProps) => {
  return (
    <S.Box active={currentCoachId === id} onClick={getHandleClickProfile(id)}>
      <S.CoachProfileImage src={imageUrl} alt="코치 프로필" />
      <S.CoachName>{nickname}</S.CoachName>
    </S.Box>
  );
};

export default CoachProfile;
