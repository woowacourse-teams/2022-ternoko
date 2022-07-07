import { CoachType } from 'types/domain';
import * as S from './styled';

export type CoachProfileProps = CoachType & {
  currentCoachId: number;
  handleClickProfile: (id: number) => void;
};

const CoachProfile = ({
  id,
  nickname,
  imageUrl,
  currentCoachId,
  handleClickProfile,
}: CoachProfileProps) => {
  return (
    <S.Box active={currentCoachId === id} onClick={() => handleClickProfile(id)}>
      <S.CoachProfileImage src={imageUrl} alt="코치 프로필" />
      <S.CoachName>{nickname}</S.CoachName>
    </S.Box>
  );
};

export default CoachProfile;
