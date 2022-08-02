import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import { ReservationType } from '@/types/domain';

import { getDateString, getTimeString } from '@/utils';

type ReservationProps = ReservationType & {
  handleClickDetailButton: () => void;
};

const Reservation = ({
  coachNickname,
  imageUrl,
  interviewStartTime,
  interviewEndTime,
  handleClickDetailButton,
}: ReservationProps) => {
  return (
    <S.Box>
      <S.ImageTextBox>
        <S.ProfileImage src={imageUrl} alt="코치 프로필" />
        <S.CoachName>{coachNickname}</S.CoachName>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage src="/assets/icon/calendar.png" alt="달력 아이콘" />
        <p>{getDateString(interviewStartTime)}</p>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <S.IconImage src="/assets/icon/clock.png" alt="시계 아이콘" />
        <p>
          {getTimeString(interviewStartTime)} ~ {getTimeString(interviewEndTime)}
        </p>
      </S.ImageTextBox>

      <S.ButtonBox>
        <Button orange={true} onClick={handleClickDetailButton}>
          <S.ButtonImage src="/assets/icon/magnifier.png" alt="돋보기 아이콘" />
          상세보기
        </Button>
        <Button orange={true}>
          <S.ButtonImage src="/assets/icon/edit.png" alt="편집 아이콘" />
          편집
        </Button>
      </S.ButtonBox>
    </S.Box>
  );
};

export default Reservation;
