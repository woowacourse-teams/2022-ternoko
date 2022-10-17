import { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import { getInterviewsAPI } from '@/Crew/api';
import Button from '@/common/components/@common/Button/styled';
import CommentModal from '@/common/components/@common/CommentModal';
import EmptyScreen from '@/common/components/@common/EmptyScreen';
import GridContainer from '@/common/components/@common/GridContainer/styled';
import InterviewDetailModal from '@/common/components/@common/InterviewDetailModal';
import useModal from '@/common/components/@common/Modal/useModal';
import Interview from '@/common/components/Interview';
import { EMPTY_SCREEN_MESSAGE, PAGE } from '@/common/constants';
import LocalStorage from '@/common/localStorage';
import { InterviewStatusType, InterviewType } from '@/common/types/domain';

export type TabMenuStatusType = 'doing' | 'done';

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
  const [TabMenuStatusType, setTabMenuStatusType] = useState<TabMenuStatusType>('doing');

  const [clickedInterviewId, setClickedInterviewId] = useState(-1);
  const [clickedInterviewStatus, setClickedInterviewStatus] =
    useState<InterviewStatusType>('EDITABLE');

  const doingInterviews = useMemo(
    () =>
      interviews.filter(
        ({ status }: InterviewType) => !['CREW_COMPLETED', 'COMPLETED'].includes(status),
      ),
    [interviews],
  );
  const doneInterviews = useMemo(
    () =>
      interviews.filter(({ status }: InterviewType) =>
        ['CREW_COMPLETED', 'COMPLETED'].includes(status),
      ),
    [interviews],
  );

  const getHandleClickTabMenu = (status: TabMenuStatusType) => () => {
    setTabMenuStatusType(status);
  };

  const getHandleClickDetailButton = (interviewId: number) => () => {
    setClickedInterviewId(interviewId);
    handleOpenModalDetail();
  };

  const getHandleClickCommentButton =
    (interviewId: number, interviewStatus: InterviewStatusType) => () => {
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
        <S.TabMenu active={TabMenuStatusType === 'doing'} onClick={getHandleClickTabMenu('doing')}>
          진행중 면담
        </S.TabMenu>
        <S.TabMenu active={TabMenuStatusType === 'done'} onClick={getHandleClickTabMenu('done')}>
          완료한 면담
        </S.TabMenu>
      </S.TabMenuBox>
      {TabMenuStatusType === 'doing' &&
        (doingInterviews.length > 0 ? (
          <GridContainer minSize="25rem" pt="4rem">
            {doingInterviews.map((interview) => (
              <Interview
                key={interview.id}
                handleClickDetailButton={getHandleClickDetailButton(interview.id)}
                handleClickCommentButton={getHandleClickCommentButton(
                  interview.id,
                  interview.status,
                )}
                {...interview}
              />
            ))}
          </GridContainer>
        ) : (
          <EmptyScreen message={EMPTY_SCREEN_MESSAGE.EMPTY_DOING_INTERVIEW} />
        ))}

      {TabMenuStatusType === 'done' &&
        (doneInterviews.length > 0 ? (
          <GridContainer minSize="25rem" pt="4rem">
            {doneInterviews.map((interview) => (
              <Interview
                key={interview.id}
                handleClickDetailButton={getHandleClickDetailButton(interview.id)}
                handleClickCommentButton={getHandleClickCommentButton(
                  interview.id,
                  interview.status,
                )}
                {...interview}
              />
            ))}
          </GridContainer>
        ) : (
          <EmptyScreen message={EMPTY_SCREEN_MESSAGE.EMPTY_DOME_INTERVIEW} />
        ))}

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
