import { Link } from 'react-router-dom';

import * as S from './styled';
import Button from '../../components/@common/Button/styled';
import CoachCalendar from '../../components/CoachCalendar';

import CalendarProvider from '../../context/CalendarProvider';

const CoachHomePage = () => {
  return (
    <>
      <S.TitleBox>
        <h2>면담 리스트</h2>
        <Link to="/coach/reservation/create">
          <Button home>스케쥴 생성</Button>
        </Link>
      </S.TitleBox>
      <CalendarProvider selectMode="single">
        <CoachCalendar />
      </CalendarProvider>
    </>
  );
};

export default CoachHomePage;
