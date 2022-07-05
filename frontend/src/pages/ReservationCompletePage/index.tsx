import Button from '../../components/@common/Button/styled';
import * as S from './styled';

const ReservationCompletePage = () => {
  return (
    <S.Box>
      <S.LogoBox>
        <S.Logo
          src="https://blog.kakaocdn.net/dn/FSvHG/btrzdoAbEI0/WA1kfeo9BFC8n8GOe39U31/img.webp"
          alt="로고"
        />
        <h2>면담 신청이 완료되었습니다.</h2>
      </S.LogoBox>

      <S.InfoContainer>
        <S.Info>
          <p>코치</p>
          <p>포코</p>
        </S.Info>
        <S.Info>
          <p>날짜</p>
          <p>2022년 07월 03일</p>
        </S.Info>
        <S.Info>
          <p>시간</p>
          <p>13 : 00 ~ 14 : 00</p>
        </S.Info>
      </S.InfoContainer>

      <S.ButtonContainer>
        <Button width="47%" height="35px" white={true}>
          면담확인
        </Button>
        <Button width="47%" height="35px">
          홈으로
        </Button>
      </S.ButtonContainer>
    </S.Box>
  );
};

export default ReservationCompletePage;
