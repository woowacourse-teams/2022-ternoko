import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import { InterviewType } from '@/types/domain';

import { PAGE } from '@/constants';
import { getDateString, getTimeString, isOverToday } from '@/utils';

type InterviewProps = InterviewType & {
  handleClickDetailButton: () => void;
  handleClickCommentButton: () => void;
};

const STATUS_MESSAGE = {
  EDITABLE: '편집 가능 ✏️',
  FIXED: '편집 불가 ❌',
  COMMENT: '코멘트 가능 💬',
  COMPLETED: '면담 완료 ✅',
  CREW_COMPLETED: '면담 완료 ✅',
  COACH_COMPLETED: '코멘트 가능 💬',
  CANCELED: '재예약 필요 👀',
} as const;

const Interview = ({
  id,
  coachNickname,
  coachImageUrl,
  status,
  interviewStartTime,
  interviewEndTime,
  handleClickDetailButton,
  handleClickCommentButton,
}: InterviewProps) => {
  return (
    <S.Box>
      <S.Tag status={status}>
        {STATUS_MESSAGE[status === 'FIXED' && isOverToday(interviewEndTime) ? 'COMMENT' : status]}
      </S.Tag>
      <S.ImageTextBox>
        <S.ProfileImage src={coachImageUrl} alt="코치 프로필" />
        <S.CoachName>{coachNickname}</S.CoachName>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <picture>
          <source srcSet="/assets/icon/calendar.avif" type="image/avif" />
          <S.IconImage src="/assets/icon/calendar.png" alt="달력 아이콘" />
        </picture>
        <S.DateText>{getDateString(interviewStartTime)}</S.DateText>
      </S.ImageTextBox>
      <S.ImageTextBox>
        <picture>
          <source srcSet="/assets/icon/clock.avif" type="image/avif" />
          <S.IconImage src="/assets/icon/clock.png" alt="시계 아이콘" />
        </picture>
        <S.DateText>
          {getTimeString(interviewStartTime)} ~ {getTimeString(interviewEndTime)}
        </S.DateText>
      </S.ImageTextBox>

      <S.ButtonBox>
        <Button orange={true} onClick={handleClickDetailButton}>
          <picture>
            <source srcSet="/assets/icon/magnifier.avif" type="image/avif" />
            <S.ButtonImage src="/assets/icon/magnifier.png" alt="돋보기 아이콘" />
          </picture>
          상세보기
        </Button>
        {['EDITABLE', 'CANCELED'].includes(status) && (
          <Link to={`${PAGE.INTERVIEW_APPLY}?interviewId=${id}`}>
            <Button orange={true}>
              <picture>
                <source srcSet="/assets/icon/edit.avif" type="image/avif" />
                <S.ButtonImage src="/assets/icon/edit.png" alt="편집 아이콘" />
              </picture>
              편집
            </Button>
          </Link>
        )}
        {isOverToday(interviewEndTime) && (
          <Button orange={true} onClick={handleClickCommentButton}>
            <picture>
              <source srcSet="/assets/icon/success.avif" type="image/avif" />
              <S.ButtonImage src="/assets/icon/success.png" alt="성공 아이콘" />
            </picture>
            코멘트
          </Button>
        )}
      </S.ButtonBox>
    </S.Box>
  );
};

export default Interview;
