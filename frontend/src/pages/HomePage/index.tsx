import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import CommentModal from '@/components/@common/CommentModal';
import GridContainer from '@/components/@common/GridContainer/styled';
import InterviewDetailModal from '@/components/@common/InterviewDetailModal';
import useModal from '@/components/@common/Modal/useModal';

import Interview from '@/components/Interview';

import { InterviewStatus, InterviewType } from '@/types/domain';

import { getInterviewsAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

export type TabMenuStatus = 'doing' | 'done';

const HomePage = () => {
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

  const [interviews, setInterviews] = useState<InterviewType[]>([]);
  const [tabMenuStatus, setTabMenuStatus] = useState<TabMenuStatus>('doing');

  const [clickedInterviewId, setClickedInterviewId] = useState(-1);
  const [clickedInterviewStatus, setClickedInterviewStatus] = useState<InterviewStatus>('EDITABLE');

  const InterviewPridicate = ({ status }: InterviewType) =>
    tabMenuStatus === 'done'
      ? ['CREW_COMPLETED', 'COMPLETE'].includes(status)
      : !['CREW_COMPLETED', 'COMPLETE'].includes(status);

  const getHandleClickTabMenu = (status: TabMenuStatus) => () => {
    setTabMenuStatus(status);
  };

  const getHandleClickDetailButton = (interviewId: number) => () => {
    setClickedInterviewId(interviewId);
    handleOpenModalDetail();
  };

  const getHandleClickCommentButton =
    (interviewId: number, interviewStatus: InterviewStatus) => () => {
      setClickedInterviewId(interviewId);
      setClickedInterviewStatus(interviewStatus);
      handleOpenModalComment();
    };

  const updateInterviews = async () => {
    const response = await getInterviewsAPI();
    setInterviews(response.data);
  };

  const afterDeleteInterview = () => {
    handleCloseModalComment();
    handleCloseModalDetail();
    updateInterviews();
  };

  const afterPostAndPutComment = () => {
    handleCloseModalComment();
    updateInterviews();
  };

  useEffect(() => {
    updateInterviews();
  }, []);

  return (
    <>
      <S.TitleBox>
        <h2>나의 면담</h2>
        <Link to={PAGE.INTERVIEW_APPLY}>
          <Button home>+ 신청하기</Button>
        </Link>
      </S.TitleBox>

      <S.TabMenuBox>
        <S.TabMenu active={tabMenuStatus === 'doing'} onClick={getHandleClickTabMenu('doing')}>
          진행중 면담
        </S.TabMenu>
        <S.TabMenu active={tabMenuStatus === 'done'} onClick={getHandleClickTabMenu('done')}>
          완료한 면담
        </S.TabMenu>
      </S.TabMenuBox>

      <GridContainer minSize="25rem" pt="4rem">
        {interviews.filter(InterviewPridicate).map((interview) => (
          <Interview
            key={interview.id}
            handleClickDetailButton={getHandleClickDetailButton(interview.id)}
            handleClickCommentButton={getHandleClickCommentButton(interview.id, interview.status)}
            {...interview}
          />
        ))}
      </GridContainer>
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

export default HomePage;
