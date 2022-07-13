import * as S from './styled';

import Button from '../../components/@common/Button/styled';

import { ReservationType } from 'types/domain';

const Reservation = ({
  coachNickname,
  imageUrl,
  interviewDate,
  interviewStartTime,
  interviewEndTime,
  location,
}: ReservationType) => {
  return (
    <S.Box>
      <S.ImageTextBox>
        <S.ProfileImage src={imageUrl} alt="코치 프로필" />
        <S.CoachName>{coachNickname}</S.CoachName>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage
          src="https://cdn-icons-png.flaticon.com/512/7088/7088625.png"
          alt="달력 아이콘"
        />
        <p>{interviewDate}</p>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage src="https://cdn-icons-png.flaticon.com/512/88/88291.png" alt="시계 아이콘" />
        <p>
          {interviewStartTime} ~ {interviewEndTime}
        </p>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage
          src="https://cdn-icons-png.flaticon.com/512/535/535239.png"
          alt="위치 아이콘"
        />
        <p>{location}</p>
      </S.ImageTextBox>

      <S.ButtonBox>
        <Button orange={true}>
          <S.ButtonImage
            src="https://cdn-icons-png.flaticon.com/512/1159/1159633.png"
            alt="편집 아이콘"
          />
          편집
        </Button>
      </S.ButtonBox>
    </S.Box>
  );
};

export default Reservation;
