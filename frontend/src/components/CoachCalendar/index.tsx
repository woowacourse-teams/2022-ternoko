import { useEffect, useMemo, useState } from 'react';

import * as S from './styled';

import * as C from '@/components/@common/CalendarStyle/styled';

import {
  monthNames,
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/context/CalendarProvider';

import { ReservationType } from '@/types/domain';

import { getCoachReservationAPI } from '@/api';
import { separateFullDate } from '@/utils';

type ScheduleType = {
  id: number;
  crewNickname: string;
  times: string[];
};

type SchedulesType = { [key: number]: ScheduleType[] };

const CoachCalendar = () => {
  const { year, month, showMonthPicker } = useCalendarState();
  const { handleClickPrevYear, handleClickNextYear, handleClickMonthPicker, getHandleClickMonth } =
    useCalendarActions();
  const { daysLength, isToday, isBeforeToday, isOverFirstDay, getDay } = useCalendarUtils();

  const [schedules, setSchedules] = useState<SchedulesType>({});

  const rerenderKey = useMemo(() => Date.now(), [year, month]);

  useEffect(() => {
    (async () => {
      const response = await getCoachReservationAPI(year, month + 1);

      const schedules = response.data.calendar.reduce(
        (
          acc: SchedulesType,
          { id, crewNickname, interviewStartTime, interviewEndTime }: ReservationType,
        ) => {
          const { day, time: startTime } = separateFullDate(interviewStartTime);
          const { time: endTime } = separateFullDate(interviewEndTime);
          const schedule = { id, crewNickname, times: [startTime, endTime] };

          acc[Number(day)] = acc[Number(day)] ? [...acc[Number(day)], schedule] : [schedule];

          return acc;
        },
        {},
      );

      setSchedules(schedules);
    })();
  }, [year, month]);

  return (
    <S.Box>
      <C.Header>
        <C.MonthPicker onClick={handleClickMonthPicker}>{monthNames[month]}</C.MonthPicker>
        <C.YearPicker>
          <C.YearChange onClick={handleClickPrevYear}>{'<'}</C.YearChange>
          <p>{year}</p>
          <C.YearChange onClick={handleClickNextYear}>{'>'}</C.YearChange>
        </C.YearPicker>
      </C.Header>

      <C.Body>
        <C.WeekDay>
          <div>Sun</div>
          <div>Mon</div>
          <div>Tue</div>
          <div>Wed</div>
          <div>Thu</div>
          <div>Fri</div>
          <div>Sat</div>
        </C.WeekDay>
        <C.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);
              const reservations = schedules[day]
                ? schedules[day].map(({ id, crewNickname, times }) => (
                    <S.Schedule key={id}>
                      {crewNickname} ({times[0]}~{times[1]})
                    </S.Schedule>
                  ))
                : [];

              if (isToday(day)) {
                return (
                  <S.CalendarDay key={index} today>
                    {day}
                    {reservations}
                  </S.CalendarDay>
                );
              }

              if (isBeforeToday(day)) {
                return (
                  <S.CalendarDay key={index} type="disable">
                    {day}
                    {reservations}
                  </S.CalendarDay>
                );
              }

              return (
                <S.CalendarDay key={index}>
                  {day}
                  {reservations}
                  <span />
                  <span />
                  <span />
                  <span />
                </S.CalendarDay>
              );
            }

            return <S.CalendarDay key={index} />;
          })}
        </C.Days>
      </C.Body>

      <C.MonthContainer show={showMonthPicker}>
        {monthNames.map((monthName, index) => (
          <div key={index} onClick={getHandleClickMonth(index)}>
            {monthName}
          </div>
        ))}
      </C.MonthContainer>
    </S.Box>
  );
};

export default CoachCalendar;
