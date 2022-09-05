import { memo, useMemo } from 'react';

import * as S from './styled';

import * as C from '@/components/@common/CalendarStyle/styled';

import {
  monthNames,
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/context/CalendarProvider';

import { DayType } from '@/types/domain';

export type CalendarProps = {
  rerenderCondition?: number;
  getHandleClickDay: (day: number) => () => void;
  getDayType: (day: number) => DayType;
  haveTimeDays: Set<number>;
};

const Calendar = ({
  rerenderCondition,
  getHandleClickDay,
  getDayType,
  haveTimeDays,
}: CalendarProps) => {
  const { year, month, showMonthPicker } = useCalendarState();
  const { handleClickPrevYear, handleClickNextYear, handleClickMonthPicker, getHandleClickMonth } =
    useCalendarActions();
  const { daysLength, isToday, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month, rerenderCondition]);

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

              if (isBelowToday(day)) {
                return (
                  <S.CalendarDay key={index} type="disable" onClick={getHandleClickDay(day)}>
                    {day}
                  </S.CalendarDay>
                );
              }

              return (
                <S.CalendarDay
                  key={index}
                  type={getDayType(day)}
                  onClick={getHandleClickDay(day)}
                  mark={haveTimeDays.has(day)}
                >
                  {day}
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

export default memo(Calendar);
