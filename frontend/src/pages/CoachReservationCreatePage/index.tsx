import { Link } from 'react-router-dom';

import * as S from './styled';

import TitleBox from '../../components/@common/TitleBox';
import Calendar from '../../components/Calendar';
import Button from '../../components/@common/Button/styled';

import useTimes from '../../hooks/useTimes';

const defaultTimes = [
  '10:00',
  '10:30',
  '11:00',
  '11:30',
  '12:00',
  '12:30',
  '13:00',
  '13:30',
  '14:00',
  '14:30',
  '15:00',
  '15:30',
  '16:00',
  '16:30',
  '17:00',
  '17:30',
];

const CoachReservationCreatePage = () => {
  const { selectedTimes, getHandleClickTime } = useTimes({ selectMode: 'multiple' });

  return (
    <>
      <TitleBox to="/" title="면담 스케쥴 만들기" />

      <S.Box>
        <S.DateBox>
          <Calendar />

          <S.TimeContainer>
            {defaultTimes.map((defaultTime, index) => (
              <S.Time
                key={index}
                active={selectedTimes.includes(defaultTime)}
                onClick={getHandleClickTime(defaultTime)}
              >
                {defaultTime}
              </S.Time>
            ))}
          </S.TimeContainer>
        </S.DateBox>
        <S.ButtonContainer>
          <Link to="/">
            <Button width="100%" height="35px" white={true}>
              홈으로
            </Button>
          </Link>

          <Button width="100%" height="35px">
            승인하기
          </Button>
        </S.ButtonContainer>
      </S.Box>
    </>
  );
};

export default CoachReservationCreatePage;
