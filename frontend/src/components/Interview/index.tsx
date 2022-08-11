import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import { InterviewType } from '@/types/domain';

import { PAGE } from '@/constants';
import { getDateString, getTimeString } from '@/utils';

type InterviewProps = InterviewType & {
  handleClickDetailButton: () => void;
  handleClickCompleteButton: () => void;
};

const Interview = ({
  id,
  coachNickname,
  coachImageUrl,
  status,
  interviewStartTime,
  interviewEndTime,
  handleClickDetailButton,
  handleClickCompleteButton,
}: InterviewProps) => {
  return (
    <S.Box status={status}>
      <S.ImageTextBox>
        <S.ProfileImage src={coachImageUrl} alt="코치 프로필" />
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
        <Link to={`${PAGE.RESERVATION_APPLY}?interviewId=${id}`}>
          <Button orange={true}>
            <S.ButtonImage src="/assets/icon/edit.png" alt="편집 아이콘" />
            편집
          </Button>
        </Link>
        <Button orange={true} onClick={handleClickCompleteButton}>
          <S.ButtonImage src="/assets/icon/success.png" alt="성공 아이콘" />
          완료
        </Button>
      </S.ButtonBox>
    </S.Box>
  );
};

export default Interview;
