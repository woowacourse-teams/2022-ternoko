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

import { CalendarTime } from '../../types/domain';

import { postCoachScheduleAPI, getCoachScheduleAPI } from '../../api';

import { separateFullDate, getFullDateString } from '../../utils';

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

const defaultCoachId = 6;

type StringDictionary = {
  [key: string]: string[];
};

const CoachReservationCreatePage = () => {
  const { year, month, selectedDates } = useCalendarState();
  const { resetSelectedDates, setDay } = useCalendarActions();
  const { isSelectedDate } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime, resetTimes, setSelectedTimes } = useTimes({
    selectMode: 'multiple',
  });
  const [calendarTimes, setCalendarTimes] = useState<CalendarTime[]>([]);
  const [isApplied, setIsApplied] = useState(false);

  const compactCalendarTimes = (times: CalendarTime[]) => {
    const result = times.reduce((acc, { year, month, times }) => {
      acc[`${year}-${month}`] = acc[`${year}-${month}`]
        ? [...acc[`${year}-${month}`], ...times]
        : times;

      return acc;
    }, {} as StringDictionary);

    return Object.entries(result).map(([yearMonth, times]) => {
      const [year, month] = yearMonth.split('-');

      return {
        year: Number(year),
        month: Number(month),
        times,
      };
    });
  };

  const getHandleClickDay = (day: number) => () => {
    const currentCalendarTime = calendarTimes.find(
      (calendarTime: CalendarTime) =>
        calendarTime.year === year && calendarTime.month === month + 1,
    );

    if (currentCalendarTime) {
      if (selectedDates.length === 2 && isSelectedDate(day)) {
        const finalDay = selectedDates.find((selectedDate) => selectedDate.day !== day)?.day;

        const times = currentCalendarTime.times
          .filter((time: string) => Number(separateFullDate(time).day) === finalDay)
          .map((fullDate: string) => separateFullDate(fullDate).time);

        setSelectedTimes(times);
      } else if (selectedDates.length >= 1) {
        setSelectedTimes([]);
      } else {
        const times = currentCalendarTime.times
          .filter((time: string) => Number(separateFullDate(time).day) === day)
          .map((fullDate: string) => separateFullDate(fullDate).time);

        setSelectedTimes(times);
      }
    }

    setDay(day);
  };

  const handleClickApplyButton = async () => {
    calendarTimes
      .filter((calendarTime: CalendarTime) =>
        selectedDates.some(
          ({ year, month }) => calendarTime.year === year && calendarTime.month === month,
        ),
      )
      .forEach((calendarTime: CalendarTime) => {
        calendarTime.times = calendarTime.times.filter((fullDate: string) => {
          const { day } = separateFullDate(fullDate);

          return selectedDates.every((selectedDate) => selectedDate.day !== Number(day));
        });
      });

    const legacyCalendarTimes = calendarTimes.filter(({ times }) => times.length !== 0);

    const clickedCalendarTimes = selectedDates
      .map(({ year, month, day }) => ({
        year,
        month,
        times: selectedTimes.map((selectTime) => getFullDateString(year, month, day, selectTime)),
      }))
      .flat();

    const body = {
      calendarTimes: compactCalendarTimes([...legacyCalendarTimes, ...clickedCalendarTimes]),
    };

    try {
      await postCoachScheduleAPI(defaultCoachId, body);
      resetSelectedDates();
      resetTimes();
      alert('??????????????????.');
      setIsApplied((prev) => !prev);
    } catch (error) {
      alert('??????????????????.');
    }
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachScheduleAPI(defaultCoachId, year, month + 1);

      const recentCalendarTimes = compactCalendarTimes(
        response.data.calendarTimes.map((calendarTime: string) => {
          const { year, month } = separateFullDate(calendarTime);

          return { year, month, times: [calendarTime] };
        }),
      );

      const oldCalendarTimes = calendarTimes.filter(
        ({ year, month }) =>
          !recentCalendarTimes.some(
            (calendarTime: CalendarTime) =>
              calendarTime.year === year && calendarTime.month === month,
          ),
      );

      setCalendarTimes([...recentCalendarTimes, ...oldCalendarTimes]);
    })();
  }, [year, month, isApplied]);

  return (
    <>
      <TitleBox to="/" title="?????? ????????? ?????????" />

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
              ?????????
            </Button>
          </Link>

          <Button width="100%" height="35px" onClick={handleClickApplyButton}>
            ????????????
          </Button>
        </S.ButtonContainer>
      </S.Box>
    </>
  );
};

export default CoachReservationCreatePage;
