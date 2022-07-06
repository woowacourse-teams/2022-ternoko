import Button from '../../components/@common/Button/styled';
import * as S from './styled';

const Reservation = () => {
  return (
    <S.Box>
      <S.ImageTextBox>
        <S.ProfileImage
          src="https://blog.kakaocdn.net/dn/FSvHG/btrzdoAbEI0/WA1kfeo9BFC8n8GOe39U31/img.webp"
          alt="코치 프로필 "
        />
        <p>카리나(장현석)</p>
      </S.ImageTextBox>

      <S.ImageTextBox>
        <S.IconImage src="https://cdn-icons-png.flaticon.com/512/7088/7088625.png" alt="" />
        <p>2022년 07월 23일</p>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage src="https://cdn-icons-png.flaticon.com/512/88/88291.png" alt="" />
        <p>13:00 ~ 14:00</p>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage src="https://cdn-icons-png.flaticon.com/512/535/535239.png" alt="" />
        <p>잠실</p>
      </S.ImageTextBox>
      <S.ButtonBox>
        <Button orange={true}>
          <S.ButtonImage src="https://cdn-icons-png.flaticon.com/512/1159/1159633.png" alt="" />
          편집
        </Button>
      </S.ButtonBox>
    </S.Box>
  );
};

export default Reservation;
