import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import GridContainer from '@/components/@common/GridContainer/styled';
import InterviewDetailModal from '@/components/@common/InterviewDetailModal';
import useModal from '@/components/@common/Modal/useModal';

import Interview from '@/components/Interview';

import { InterviewType } from '@/types/domain';

import { getInterviewsAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

export type TabMenuStatus = 'doing' | 'done';

const HomePage = () => {
  const memberRole = LocalStorage.getMemberRole();
  const { show, display, handleOpenModal, handleCloseModal } = useModal();

  const [interviews, setInterviews] = useState<InterviewType[]>([]);
  const [tabMenuStatus, setTabMenuStatus] = useState<TabMenuStatus>('doing');

  const [clickedInterviewId, setClickedInterviewId] = useState(-1);

  const getHandleClickTabMenu = (status: TabMenuStatus) => () => {
    setTabMenuStatus(status);
  };

  const getHandleClickDetailButton = (id: number) => () => {
    setClickedInterviewId(id);
    handleOpenModal();
  };

  const updateInterviews = async () => {
    const response = await getInterviewsAPI();
    setInterviews(response.data);
  };

  const afterDeleteInterview = () => {
    handleCloseModal();
    updateInterviews();
  };

  useEffect(() => {
    updateInterviews();
  }, []);

  return (
    <>
      <S.TitleBox>
        <h2>나의 면담</h2>
        <Link to={PAGE.RESERVATION_APPLY}>
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
        {interviews.map((interview) => (
          <Interview
            key={interview.id}
            handleClickDetailButton={getHandleClickDetailButton(interview.id)}
            {...interview}
          />
        ))}
      </GridContainer>
      <InterviewDetailModal
        show={show}
        display={display}
        role={memberRole}
        interviewId={clickedInterviewId}
        afterDeleteInterview={afterDeleteInterview}
        handleCloseModal={handleCloseModal}
      />
    </>
  );
};

export default HomePage;
