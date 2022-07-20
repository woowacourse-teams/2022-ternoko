import { useState, useMemo, useEffect } from 'react';

import * as S from './styled';

import {
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
  monthNames,
} from '../../context/CalendarProvider';

import { getCoachReservationAPI } from '../../api';

import { ReservationResponseType } from '../../types/domain';

import { separateFullDate } from '../../utils';

const defaultCoachId = 12;

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
      const response = await getCoachReservationAPI(defaultCoachId, year, month + 1);

      const schedules = response.data.calendar.reduce(
        (
          acc: SchedulesType,
          { id, crewNickname, interviewStartTime, interviewEndTime }: ReservationResponseType,
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
      <S.Header>
        <S.MonthPicker onClick={handleClickMonthPicker}>{monthNames[month]}</S.MonthPicker>
        <S.YearPicker>
          <S.YearChange onClick={handleClickPrevYear}>{'<'}</S.YearChange>
          <p>{year}</p>
          <S.YearChange onClick={handleClickNextYear}>{'>'}</S.YearChange>
        </S.YearPicker>
      </S.Header>

      <S.Body>
        <S.WeekDay>
          <div>Sun</div>
          <div>Mon</div>
          <div>Tue</div>
          <div>Wed</div>
          <div>Thu</div>
          <div>Fri</div>
          <div>Sat</div>
        </S.WeekDay>
        <S.Days key={rerenderKey}>
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
                  <S.Day key={index} today>
                    {day}
                    {reservations}
                  </S.Day>
                );
              }

              if (isBeforeToday(day)) {
                return (
                  <S.Day key={index} type="disable">
                    {day}
                    {reservations}
                  </S.Day>
                );
              }

              return (
                <S.Day key={index}>
                  {day}
                  {reservations}
                  <span />
                  <span />
                  <span />
                  <span />
                </S.Day>
              );
            }

            return <S.Day key={index} />;
          })}
        </S.Days>
      </S.Body>

      <S.MonthContainer show={showMonthPicker}>
        {monthNames.map((monthName, index) => (
          <div key={index} onClick={getHandleClickMonth(index)}>
            {monthName}
          </div>
        ))}
      </S.MonthContainer>
    </S.Box>
  );
};

export default CoachCalendar;
