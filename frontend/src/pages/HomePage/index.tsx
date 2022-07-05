import Reservation from '../../components/Reservation';
import Button from '../../components/@common/Button/styled';
import * as S from './styled';

const HomePage = () => {
  return (
    <S.Box>
      <S.TitleBox>
        <h2>나의 면담</h2>
        <Button>+ 신청하기</Button>
      </S.TitleBox>
      <S.TabMenuBox>
        <h3>진행중 면담</h3>
        <h3>완료한 면담</h3>
      </S.TabMenuBox>
      <S.ReservationContainer>
        <Reservation />
        <Reservation />
        <Reservation />
        <Reservation />
      </S.ReservationContainer>
    </S.Box>
  );
};

export default HomePage;
