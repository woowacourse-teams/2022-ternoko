import Button from '../../components/@common/Button/styled';
import CoachProfile from '../../components/CoachProfile';
import * as S from './styled';

const ReservationApplyPage = () => {
  return (
    <S.Box>
      <S.TitleBox>
        <h2>{'<'} 면담 신청하기</h2>
      </S.TitleBox>
      <S.SubTitleBox>
        <S.Circle>1</S.Circle>
        <h3>코치를 선택해주세요.</h3>
      </S.SubTitleBox>
      <S.CoachProfileContainer>
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
        <CoachProfile />
      </S.CoachProfileContainer>

      <Button width="100%" height="40px">
        다음
      </Button>
    </S.Box>
  );
};

export default ReservationApplyPage;
