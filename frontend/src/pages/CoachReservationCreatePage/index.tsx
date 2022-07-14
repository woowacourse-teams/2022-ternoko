import { Link } from 'react-router-dom';

import * as S from './styled';

import TitleBox from '../../components/@common/TitleBox';
import Button from '../../components/@common/Button/styled';
import ScrollContainer from '../../components/@common/ScrollContainer/styled';
import Time from '../../components/Time/styled';
import Calendar from '../../components/Calendar';

import useTimes from '../../hooks/useTimes';

import { useCalendarState, useCalendarActions } from '../../context/CalendarProvider';

import { postCoachScheduleAPI } from '../../api';

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
  const { selectedDates } = useCalendarState();
  const { resetSelectedDates } = useCalendarActions();
  const { selectedTimes, getHandleClickTime, resetTimes } = useTimes({ selectMode: 'multiple' });

  const handleClickApplyButton = async () => {
    const coachId = 12;
    const calendarTimes = selectedDates
      .map(({ year, month, day }) =>
        selectedTimes.map(
          (selectTime) =>
            `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(
              2,
              '0',
            )} ${selectTime}`,
        ),
      )
      .flat();

    const body = {
      calendarTimes,
    };

    try {
      await postCoachScheduleAPI(coachId, body);
      resetSelectedDates();
      resetTimes();

      alert('등록됐엉~');
    } catch (error) {
      alert('실패했엉~');
    }
  };

  return (
    <>
      <TitleBox to="/" title="면담 스케쥴 만들기" />

      <S.Box>
        <S.DateBox>
          <Calendar />

          <ScrollContainer>
            {defaultTimes.map((defaultTime, index) => (
              <Time
                key={index}
                active={selectedTimes.includes(defaultTime)}
                onClick={getHandleClickTime(defaultTime)}
              >
                {defaultTime}
              </Time>
            ))}
          </ScrollContainer>
        </S.DateBox>
        <S.ButtonContainer>
          <Link to="/">
            <Button width="100%" height="35px" white={true}>
              홈으로
            </Button>
          </Link>

          <Button width="100%" height="35px" onClick={handleClickApplyButton}>
            승인하기
          </Button>
        </S.ButtonContainer>
      </S.Box>
    </>
  );
};

export default CoachReservationCreatePage;
