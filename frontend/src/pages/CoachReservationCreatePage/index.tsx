import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import TitleBox from '../../components/@common/TitleBox';
import Button from '../../components/@common/Button/styled';
import ScrollContainer from '../../components/@common/ScrollContainer/styled';
import Time from '../../components/Time/styled';
import Calendar from '../../components/Calendar';

import useTimes from '../../hooks/useTimes';

import {
  useCalendarState,
  useCalendarActions,
  useCalendarUtils,
} from '../../context/CalendarProvider';

import { postCoachScheduleAPI, getCoachScheduleAPI } from '../../api';

import { separateFullDate } from '../../utils';

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

const defaultCoachId = 12;

type CalendarTimes = {
  [key: string]: string[];
};

const CoachReservationCreatePage = () => {
  const { year, month, selectedDates } = useCalendarState();
  const { resetSelectedDates, setDay } = useCalendarActions();
  const { isSelectedDate } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime, resetTimes, setSelectedTimes } = useTimes({
    selectMode: 'multiple',
  });

  const [calendarTimes, setCalendarTimes] = useState<CalendarTimes>({});

  const handleClickApplyButton = async () => {
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
      await postCoachScheduleAPI(defaultCoachId, body);
      resetSelectedDates();
      resetTimes();

      alert('등록됐엉~');
    } catch (error) {
      alert('실패했엉~');
    }
  };

  const getHandleClickDay = (day: number) => () => {
    const selectedTimes = isSelectedDate(day) ? [] : calendarTimes[day] ?? [];
    setSelectedTimes(selectedTimes);
    setDay(day);
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachScheduleAPI(defaultCoachId, year, month);

      setCalendarTimes(
        response.data.calendarTimes.reduce((calendarTimes: CalendarTimes, date: string) => {
          const { day, time } = separateFullDate(date);

          calendarTimes[day] = calendarTimes[day] ?? [];
          calendarTimes[day].push(time);

          return calendarTimes;
        }, {}),
      );
    })();
  }, [year, month]);

  return (
    <>
      <TitleBox to="/" title="면담 스케쥴 만들기" />

      <S.Box>
        <S.DateBox>
          <Calendar getHandleClickDay={getHandleClickDay} />

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
