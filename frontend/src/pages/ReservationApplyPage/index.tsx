import Button from '../../components/@common/Button/styled';
import CoachProfile from '../../components/CoachProfile';
import GridContainer from '../../components/@common/GridContainer/styled';
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

      <GridContainer minSize="110px" pb="3rem">
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
      </GridContainer>

      <Button width="100%" height="40px">
        다음
      </Button>
    </S.Box>
  );
};

export default ReservationApplyPage;
