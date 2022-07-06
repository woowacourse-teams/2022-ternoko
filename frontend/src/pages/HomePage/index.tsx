import { useState } from 'react';
import { Link } from 'react-router-dom';

import Reservation from '../../components/Reservation';
import Button from '../../components/@common/Button/styled';
import GridContainer from '../../components/@common/GridContainer/styled';
import * as S from './styled';

export type TabMenuStatus = 'doing' | 'done';

const HomePage = () => {
  const [tabMenuStatus, setTabMenuStatus] = useState<TabMenuStatus>('doing');

  const handleClickTabMenu = (status: TabMenuStatus) => {
    setTabMenuStatus(status);
  };

  return (
    <>
      <S.TitleBox>
        <h2>나의 면담</h2>
        <Link to="/reservation/apply">
          <Button>+ 신청하기</Button>
        </Link>
      </S.TitleBox>
      <S.TabMenuBox>
        <S.TabMenu active={tabMenuStatus === 'doing'} onClick={() => handleClickTabMenu('doing')}>
          진행중 면담
        </S.TabMenu>
        <S.TabMenu active={tabMenuStatus === 'done'} onClick={() => handleClickTabMenu('done')}>
          완료한 면담
        </S.TabMenu>
      </S.TabMenuBox>
      <GridContainer minSize="25rem" pt="4rem">
        <Reservation />
        <Reservation />
        <Reservation />
        <Reservation />
      </GridContainer>
    </>
  );
};

export default HomePage;
