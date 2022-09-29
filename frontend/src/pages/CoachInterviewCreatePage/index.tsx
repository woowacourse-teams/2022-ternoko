import { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import ScrollContainer from '@/components/@common/ScrollContainer/styled';
import TitleBox from '@/components/@common/TitleBox';

import Calendar from '@/components/Calendar';
import Time from '@/components/Time/styled';

import useTimes from '@/hooks/useTimes';

import { useCalendarActions, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';
import { useLoadingActions } from '@/context/LoadingProvider';
import { useToastActions } from '@/context/ToastProvider';
import { useUserState } from '@/context/UserProvider';

import {
  CalendarTime,
  CoachScheduleRequestCalendarTime,
  CrewSelectTime,
  OneWeekDayType,
  StringDictionary,
} from '@/types/domain';

import { getCoachScheduleAPI, postCoachScheduleAPI } from '@/api';
import { ERROR_MESSAGE, INITIAL_COACH_ID, PAGE, SUCCESS_MESSAGE } from '@/constants';
import { getFullDateString, separateFullDate } from '@/utils';

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

const CoachInterviewCreatePage = () => {
  const { id } = useUserState();
  const { year, month, selectedDates } = useCalendarState();
  const { resetSelectedDates, setDay, addSelectedDates, removeSelectedDates } =
    useCalendarActions();
  const { onLoading, offLoading } = useLoadingActions();
  const { isSelectedDate, isBelowToday } = useCalendarUtils();
  const { selectedTimes, getHandleClickTime, resetTimes, setSelectedTimes } = useTimes({
    selectMode: 'MULTIPLE',
  });
  const isAllSelectedTimes = selectedTimes.length === defaultTimes.length;

  const { showToast } = useToastActions();

  const [calendarTimes, setCalendarTimes] = useState<CalendarTime[]>([]);

  const [isApplied, setIsApplied] = useState(false);

  const haveTimeDays = useMemo(
    () =>
      new Set(
        calendarTimes
          .find((calendarTime) => calendarTime.year === year && calendarTime.month === month + 1)
          ?.times.map((time) => Number(separateFullDate(time).day)) ?? [],
      ),
    [calendarTimes],
  );

  const getDayType = (day: number) => (isSelectedDate(day) ? 'active' : 'default');

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

  const getHandleClickDayOfWeek = (startDay: OneWeekDayType) => () => {
    const lastDay = new Date(year, month + 1, 0).getDate();
    let isAdd = false;

    for (let day = startDay; day <= lastDay; day += 7) {
      if (!isBelowToday(day) && !isSelectedDate(day)) {
        isAdd = true;

        break;
      }
    }

    const dates = [];

    if (isAdd) {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (
          !isBelowToday(day) &&
          selectedDates.every(
            (selectedDate) =>
              selectedDate.year !== year ||
              selectedDate.month !== month ||
              selectedDate.day !== day,
          )
        ) {
          dates.push({ year, month: month + 1, day });
        }
      }

      dates.length && addSelectedDates(dates);
    } else {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (!isBelowToday(day)) {
          dates.push({ year, month: month + 1, day });
        }
      }

      dates.length && removeSelectedDates(dates);
    }

    dates.length && resetTimes();
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
        resetTimes();
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
    if (!selectedDates.length) return;

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
      calendarTimes: compactCalendarTimes([...legacyCalendarTimes, ...clickedCalendarTimes]).map(
        (calendarTime: CalendarTime): CoachScheduleRequestCalendarTime => ({
          ...calendarTime,
          times: calendarTime.times.map((time: string) => ({
            time,
            availableDateTimeStatus: 'OPEN',
          })),
        }),
      ),
    };

    try {
      onLoading();
      await postCoachScheduleAPI(body);
      offLoading();
      resetSelectedDates();
      resetTimes();
      showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_SCHEDULE);
      setIsApplied((prev) => !prev);
    } catch (error) {
      offLoading();
      showToast('ERROR', ERROR_MESSAGE.CREATE_SCHEDULE);
    } finally {
      offLoading();
    }
  };

  const handleClickAllTimeButton = () => {
    setSelectedTimes(isAllSelectedTimes ? [] : [...defaultTimes]);
  };

  useEffect(() => {
    if (id === INITIAL_COACH_ID) return;

    (async () => {
      // 추후 response 타입 필요
      const response = await getCoachScheduleAPI(id, year, month + 1);

      const recentCalendarTimes = compactCalendarTimes(
        response.data.calendarTimes.map(({ calendarTime }: CrewSelectTime) => {
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
  }, [year, month, isApplied, id]);

  return (
    <S.Box>
      <S.HeaderBox>
        <TitleBox to={PAGE.COACH_HOME}>면담 스케쥴 만들기</TitleBox>
        <Button onClick={() => alert('해당 기능은 준비중입니다.')}>
          {month + 1}월 한눈에 보기
        </Button>
      </S.HeaderBox>

      <S.DateBox>
        <Calendar
          getHandleClickDayOfWeek={getHandleClickDayOfWeek}
          getHandleClickDay={getHandleClickDay}
          getDayType={getDayType}
          haveTimeDays={haveTimeDays}
        />

        <ScrollContainer>
          <S.AllTimeButton active={isAllSelectedTimes} onClick={handleClickAllTimeButton}>
            {isAllSelectedTimes ? '전체 해제' : '전체 선택'}
          </S.AllTimeButton>

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
        <Link to={PAGE.COACH_HOME}>
          <Button width="100%" height="35px" white={true}>
            홈으로
          </Button>
        </Link>

        <Button
          width="100%"
          height="35px"
          onClick={handleClickApplyButton}
          inActive={selectedDates.length === 0}
        >
          스케쥴 저장
        </Button>
      </S.ButtonContainer>
    </S.Box>
  );
};

export default CoachInterviewCreatePage;
