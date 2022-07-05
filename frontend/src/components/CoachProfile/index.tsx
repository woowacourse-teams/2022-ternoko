import * as S from './styled';

const CoachProfile = () => {
  return (
    <S.Box>
      <S.CoachProfileImage
        src="https://blog.kakaocdn.net/dn/FSvHG/btrzdoAbEI0/WA1kfeo9BFC8n8GOe39U31/img.webp"
        alt="코치 프로필"
      />
      <S.CoachName>카리나(바니)</S.CoachName>
    </S.Box>
  );
};

export default CoachProfile;
