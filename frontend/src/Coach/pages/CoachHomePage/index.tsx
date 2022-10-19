import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import ScheduleCalendar from '@/Coach/components/ScheduleCalendar';

import Button from '@/Shared/components/Button/styled';
import CommentModal from '@/Shared/components/CommentModal';
import InterviewDetailModal from '@/Shared/components/InterviewDetailModal';
import useModal from '@/Shared/components/Modal/useModal';

import CalendarProvider from '@/Shared/context/CalendarProvider';

import { PATH } from '@/Shared/constants/path';
import LocalStorage from '@/Shared/localStorage';

import { InterviewStatusType } from '@/Types/domain';

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
        <Link to={PATH.COACH_INTERVIEW_CREATE}>
          <Button home>스케쥴 생성</Button>
        </Link>
      </S.TitleBox>
      <CalendarProvider selectMode="SINGLE">
        <ScheduleCalendar
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
