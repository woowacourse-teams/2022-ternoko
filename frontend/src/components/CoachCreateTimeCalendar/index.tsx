import { memo, useMemo } from 'react';

import * as S from './styled';

import Calendar from '@/components/@common/Calendar';
import * as C from '@/components/@common/CrewAndCoachCalendarStyle/styled';

import { useCalendarActions, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';

import { OneWeekDayType } from '@/types/domain';

import { convertMonthToMonthIndex, generateDayOfWeekWithStartDay, isSunDay } from '@/utils';

export type CoachCreateTimeCalendarProps = {
  onChangeSelectedDatesByClickDayOfWeek: () => void;
  getHandleClickDay: (day: number) => () => void;
  haveTimeDays: Set<number>;
};

const CoachCreateTimeCalendar = ({
  onChangeSelectedDatesByClickDayOfWeek,
  getHandleClickDay,
  haveTimeDays,
}: CoachCreateTimeCalendarProps) => {
  const { year, month } = useCalendarState();
  const { addSelectedDates, removeSelectedDates } = useCalendarActions();
  const { daysLength, isBelowToday, isOverFirstDay, getDay, isSelectedDate } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month]);

  const dayOfWeekWithStartDay = useMemo(
    () => generateDayOfWeekWithStartDay(year, month),
    [year, month],
  );

  const isDateNotInOfSelectedDatesAfterToday = (day: number) =>
    !isBelowToday(day) && !isSelectedDate(day);

  const isDateInOfSelectedDatesAfterToday = (day: number) =>
    !isBelowToday(day) && isSelectedDate(day);

  const getHandleClickDayOfWeek = (startDay: OneWeekDayType) => () => {
    const lastDay = new Date(year, month, 0).getDate();
    let isAllSelect = false;

    for (let day = startDay; day <= lastDay; day += 7) {
      if (isDateNotInOfSelectedDatesAfterToday(day)) {
        isAllSelect = true;

        break;
      }
    }

    const dates = [];

    if (isAllSelect) {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (isDateNotInOfSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }
      }

      dates.length && addSelectedDates(dates);
    } else {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (isDateInOfSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }
      }

      dates.length && removeSelectedDates(dates);
    }

    dates.length && onChangeSelectedDatesByClickDayOfWeek();
  };

  const checkIsAllSelectedColumn = (startDay: OneWeekDayType) => {
    const lastDay = new Date(year, month, 0).getDate();
    let isAllSelectedColumn = true;

    for (let day = startDay; day <= lastDay; day += 7) {
      if (isDateNotInOfSelectedDatesAfterToday(day)) {
        isAllSelectedColumn = false;

        break;
      }
    }

    return isAllSelectedColumn;
  };

  const getHandleClickSelectRowButton = (startDay: number) => () => {
    const restDayUntilSunDay =
      7 - new Date(year, convertMonthToMonthIndex(month), startDay).getDay();
    let isAllSelect = false;

    for (let day = startDay; day < startDay + restDayUntilSunDay; day++) {
      if (!isBelowToday(day) && !isSelectedDate(day)) {
        isAllSelect = true;

        break;
      }
    }

    const dates = [];

    if (isAllSelect) {
      for (let day = startDay; day < startDay + restDayUntilSunDay; day++) {
        if (isDateNotInOfSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }
      }

      dates.length && addSelectedDates(dates);
    } else {
      for (let day = startDay; day < startDay + restDayUntilSunDay; day++) {
        if (isDateInOfSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }
      }

      dates.length && removeSelectedDates(dates);
    }

    dates.length && onChangeSelectedDatesByClickDayOfWeek();
  };

  const checkIsAllSelectedRow = (startDay: number) => {
    const restDayUntilSunDay =
      7 - new Date(year, convertMonthToMonthIndex(month), startDay).getDay();
    let isAllSelectedRow = true;

    for (let day = startDay; day < startDay + restDayUntilSunDay; day++) {
      if (isDateNotInOfSelectedDatesAfterToday(day)) {
        isAllSelectedRow = false;

        break;
      }
    }

    return isAllSelectedRow;
  };

  return (
    <C.Box>
      <Calendar>
        <S.WeekDay>
          <div />
          {dayOfWeekWithStartDay.map(({ name, startDay }) => (
            <div key={name}>
              {
                <S.AllTimeButton
                  key={startDay}
                  onClick={getHandleClickDayOfWeek(startDay)}
                  active={checkIsAllSelectedColumn(startDay)}
                >
                  ✅
                </S.AllTimeButton>
              }
              <p>{name}</p>
            </div>
          ))}
        </S.WeekDay>
        <S.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            const day = getDay(index);
            const selectRowButton = isSunDay(year, month, day) ? (
              <S.AllTimeButton
                key={day}
                onClick={getHandleClickSelectRowButton(day)}
                active={checkIsAllSelectedRow(day)}
              >
                ✅
              </S.AllTimeButton>
            ) : (
              <></>
            );

            if (isOverFirstDay(index)) {
              if (isBelowToday(day)) {
                return (
                  <>
                    {selectRowButton}
                    <C.CalendarDay key={index} type="disable">
                      {day}
                    </C.CalendarDay>
                  </>
                );
              }

              return (
                <>
                  {selectRowButton}
                  <C.CalendarDay
                    key={index}
                    type={isSelectedDate(day) ? 'active' : 'default'}
                    onClick={getHandleClickDay(day)}
                    mark={haveTimeDays.has(day)}
                  >
                    {day}
                    <span />
                    <span />
                    <span />
                    <span />
                  </C.CalendarDay>
                </>
              );
            }

            return (
              <>
                {selectRowButton}
                <C.CalendarDay key={index} />
              </>
            );
          })}
        </S.Days>
      </Calendar>
    </C.Box>
  );
};

export default memo(CoachCreateTimeCalendar);
