import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import CommentModal from '@/components/@common/CommentModal';
import InterviewDetailModal from '@/components/@common/InterviewDetailModal';
import useModal from '@/components/@common/Modal/useModal';

import CoachCalendar from '@/components/CoachCalendar';

import CalendarProvider from '@/context/CalendarProvider';

import { InterviewStatus } from '@/types/domain';

import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const CoachHomePage = () => {
  const memberRole = LocalStorage.getMemberRole();
  const {
    show: showDetail,
    display: displayDetail,
    handleOpenModal: handleOpenModalDetail,
    handleCloseModal: handleCloseModalDetail,
  } = useModal();
  const {
    show: showComment,
    display: displayComment,
    handleOpenModal: handleOpenModalComment,
    handleCloseModal: handleCloseModalComment,
  } = useModal();
  const [clickedInterviewId, setClickedInterviewId] = useState(-1);
  const [clickedInterviewStatus, setClickedInterviewStatus] = useState<InterviewStatus>('EDITABLE');
  const calendarRerenderkeyRef = useRef(Date.now());

  const getHandleClickSchedule = (id: number) => () => {
    setClickedInterviewId(id);
    handleOpenModalDetail();
  };

  const afterDeleteInterview = () => {
    calendarRerenderkeyRef.current = Date.now();
    handleOpenModalDetail();
  };

  const getHandleClickCommentButton = (status: InterviewStatus | null) => () => {
    status && setClickedInterviewStatus(status);
    handleOpenModalComment();
  };

  return (
    <>
      <S.TitleBox>
        <h2>면담 리스트</h2>
        <Link to={PAGE.COACH_INTERVIEW_CREATE}>
          <Button home>스케쥴 생성</Button>
        </Link>
      </S.TitleBox>
      <CalendarProvider selectMode="SINGLE">
        <CoachCalendar
          key={calendarRerenderkeyRef.current}
          getHandleClickSchedule={getHandleClickSchedule}
          getHandleClickCommentButton={getHandleClickCommentButton}
        />
      </CalendarProvider>
      <InterviewDetailModal
        show={showDetail}
        display={displayDetail}
        role={memberRole}
        interviewId={clickedInterviewId}
        afterDeleteInterview={afterDeleteInterview}
        handleCloseModal={handleCloseModalDetail}
      />
      <CommentModal
        show={showComment}
        display={displayComment}
        memberRole={memberRole}
        interviewId={clickedInterviewId}
        interviewStatus={clickedInterviewStatus}
        handleCloseModal={handleCloseModalComment}
      />
    </>
  );
};

export default CoachHomePage;
