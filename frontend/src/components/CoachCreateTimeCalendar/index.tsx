import { memo, useMemo } from 'react';

import * as S from './styled';

import Calendar from '@/components/@common/Calendar';
import * as C from '@/components/@common/CrewAndCoachCalendarStyle/styled';

import { useCalendarActions, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';

import { OneWeekDayType } from '@/types/domain';

import { generateDayOfWeekWithStartDay } from '@/utils';

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
  const { year, month, selectedDates } = useCalendarState();
  const { addSelectedDates, removeSelectedDates } = useCalendarActions();
  const { daysLength, isBelowToday, isOverFirstDay, getDay, isSelectedDate, isSameDate } =
    useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month]);

  const dayOfWeekWithStartDay = useMemo(
    () => generateDayOfWeekWithStartDay(year, month),
    [year, month],
  );

  const isDateNotInSelectedDatesAfterToday = (day: number) =>
    !isBelowToday(day) && selectedDates.every((selectedDate) => !isSameDate(selectedDate, day));

  const getHandleClickDayOfWeek = (startDay: OneWeekDayType) => () => {
    const lastDay = new Date(year, month, 0).getDate();
    let isAllSelect = false;

    for (let day = startDay; day <= lastDay; day += 7) {
      if (!isBelowToday(day) && !isSelectedDate(day)) {
        isAllSelect = true;

        break;
      }
    }

    const dates = [];

    if (isAllSelect) {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (isDateNotInSelectedDatesAfterToday(day)) {
          dates.push({ year, month, day });
        }
      }

      dates.length && addSelectedDates(dates);
    } else {
      for (let day = startDay; day <= lastDay; day += 7) {
        if (!isBelowToday(day)) {
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
      if (isDateNotInSelectedDatesAfterToday(day)) {
        isAllSelectedColumn = false;

        break;
      }
    }

    return isAllSelectedColumn;
  };

  return (
    <C.Box>
      <Calendar>
        <S.WeekDay>
          {dayOfWeekWithStartDay.map(({ name, startDay }) => (
            <div key={name}>
              {getHandleClickDayOfWeek && (
                <S.AllTimeButton
                  key={startDay}
                  onClick={getHandleClickDayOfWeek(startDay)}
                  active={checkIsAllSelectedColumn(startDay)}
                >
                  ✅
                </S.AllTimeButton>
              )}
              <p>{name}</p>
            </div>
          ))}
        </S.WeekDay>
        <S.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);

              if (isBelowToday(day)) {
                return (
                  <C.CalendarDay key={index} type="disable">
                    {day}
                  </C.CalendarDay>
                );
              }

              return (
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
              );
            }

            return <C.CalendarDay key={index} />;
          })}
        </S.Days>
      </Calendar>
    </C.Box>
  );
};

export default memo(CoachCreateTimeCalendar);
