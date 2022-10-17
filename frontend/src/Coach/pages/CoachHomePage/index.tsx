import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import CoachScheduleCalendar from '@/Coach/components/CoachScheduleCalendar';
import Button from '@/common/components/@common/Button/styled';
import CommentModal from '@/common/components/@common/CommentModal';
import InterviewDetailModal from '@/common/components/@common/InterviewDetailModal';
import useModal from '@/common/components/@common/Modal/useModal';
import { PAGE } from '@/common/constants';
import CalendarProvider from '@/common/context/CalendarProvider';
import LocalStorage from '@/common/localStorage';
import { InterviewStatusType } from '@/common/types/domain';

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
  const [clickedInterviewStatus, setClickedInterviewStatus] =
    useState<InterviewStatusType>('EDITABLE');
  const calendarRerenderkeyRef = useRef(Date.now());

  const getHandleClickSchedule = (interviewId: number) => () => {
    setClickedInterviewId(interviewId);
    handleOpenModalDetail();
  };

  const afterDeleteInterview = () => {
    calendarRerenderkeyRef.current = Date.now();
    handleCloseModalDetail();
  };

  const afterPostAndPutComment = () => {
    calendarRerenderkeyRef.current = Date.now();
    handleCloseModalComment();
  };

  const getHandleClickCommentButton =
    (interviewId: number, status: InterviewStatusType = 'EDITABLE') =>
    () => {
      setClickedInterviewId(interviewId);
      setClickedInterviewStatus(status);
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
        <CoachScheduleCalendar
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
        afterPostAndPutComment={afterPostAndPutComment}
        handleCloseModal={handleCloseModalComment}
      />
    </>
  );
};

export default CoachHomePage;
