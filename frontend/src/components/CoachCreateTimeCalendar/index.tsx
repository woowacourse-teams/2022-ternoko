import { memo, useMemo } from 'react';

import * as S from './styled';

import Calendar from '@/components/@common/Calendar';
import * as C from '@/components/@common/CrewAndCoachCalendarStyle/styled';

import { useCalendarActions, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';

import { DateType, OneWeekDayType } from '@/types/domain';

import { convertMonthToMonthIndex, generateDayOfWeekWithStartDay, isSunDay } from '@/utils';

type ForEachCellOfLineType = (predicate: (day: number) => boolean) => void;

export type CoachCreateTimeCalendarProps = {
  onChangeDateLine: () => void;
  getHandleClickDay: (day: number) => () => void;
  haveTimeDays: Set<number>;
};

const CoachCreateTimeCalendar = ({
  onChangeDateLine,
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

  const isDateNotInSelectedDatesAfterToday = (day: number) =>
    !isBelowToday(day) && !isSelectedDate(day);

  const isDateInSelectedDatesAfterToday = (day: number) =>
    !isBelowToday(day) && isSelectedDate(day);

  const isCheckedLine = (forEachCellOfLine: ForEachCellOfLineType) => {
    let result = true;

    forEachCellOfLine((day) => {
      if (isDateNotInSelectedDatesAfterToday(day)) {
        result = false;

        return false;
      }

      return true;
    });

    return result;
  };

  const selectDateLine = (forEachCellOfLine: ForEachCellOfLineType) => {
    const dates: DateType[] = [];

    if (isCheckedLine(forEachCellOfLine)) {
      forEachCellOfLine((day) => {
        if (isDateInSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }

        return true;
      });

      dates.length && removeSelectedDates(dates);
    } else {
      forEachCellOfLine((day) => {
        if (isDateNotInSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }

        return true;
      });

      dates.length && addSelectedDates(dates);
    }

    dates.length && onChangeDateLine();
  };

  const getForEachCellOfColumn = (startDay: OneWeekDayType): ForEachCellOfLineType => {
    const lastDay = new Date(year, month, 0).getDate();

    return (predicate) => {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (!predicate(day)) break;
      }
    };
  };

  const getForEachCellOfRow = (startDay: number): ForEachCellOfLineType => {
    const lastDay = new Date(year, month, 0).getDate();
    const restDayUntilSunDay =
      6 - new Date(year, convertMonthToMonthIndex(month), startDay).getDay();
    const until = Math.min(lastDay, startDay + restDayUntilSunDay);

    return (predicate) => {
      for (let day = startDay; day <= until; day++) {
        if (!predicate(day)) break;
      }
    };
  };

  const getHandleClickDayOfWeek = (startDay: OneWeekDayType) => () =>
    selectDateLine(getForEachCellOfColumn(startDay));

  const checkIsAllSelectedColumn = (startDay: OneWeekDayType) =>
    isCheckedLine(getForEachCellOfColumn(startDay));

  const getHandleClickSelectRowButton = (startDay: number) => () =>
    selectDateLine(getForEachCellOfRow(startDay));

  const checkIsAllSelectedRow = (startDay: number) => isCheckedLine(getForEachCellOfRow(startDay));

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
