import { memo, useMemo } from 'react';

import * as S from './styled';

import * as C from '@/components/@common/CalendarStyle/styled';

import {
  monthNames,
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/context/CalendarProvider';

import { DayType, OneWeekDayType } from '@/types/domain';

import { generateDayOfWeekWithStartDay } from '@/utils';

export type CalendarProps = {
  rerenderCondition?: number;
  getHandleClickDayOfWeek?: (startDay: OneWeekDayType) => () => void;
  getHandleClickDay: (day: number) => () => void;
  getDayType: (day: number) => DayType;
  haveTimeDays: Set<number>;
};

const Calendar = ({
  rerenderCondition,
  getHandleClickDayOfWeek,
  getHandleClickDay,
  getDayType,
  haveTimeDays,
}: CalendarProps) => {
  const { year, month, showMonthPicker } = useCalendarState();
  const {
    handleClickPrevYear,
    handleClickNextYear,
    handleClickPrevMonth,
    handleClickNextMonth,
    handleClickMonthPicker,
    getHandleClickMonth,
  } = useCalendarActions();
  const { daysLength, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month, rerenderCondition]);

  const dayOfWeekWithStartDay = generateDayOfWeekWithStartDay(year, month);

  return (
    <S.Box>
      <C.Header>
        <C.YearPicker>
          <C.DateChange onClick={handleClickPrevYear}>{'<'}</C.DateChange>
          <p>{year}</p>
          <C.DateChange onClick={handleClickNextYear}>{'>'}</C.DateChange>
        </C.YearPicker>

        <C.MonthPicker>
          <C.DateChange onClick={handleClickPrevMonth}>{'<'}</C.DateChange>
          <p onClick={handleClickMonthPicker}>{monthNames[month]}</p>
          <C.DateChange onClick={handleClickNextMonth}>{'>'}</C.DateChange>
        </C.MonthPicker>
      </C.Header>

      <C.Body>
        <C.WeekDay>
          {dayOfWeekWithStartDay.map(({ name, startDay }) => (
            <S.DayOfWeek
              key={startDay}
              onClick={
                getHandleClickDayOfWeek === undefined ? () => {} : getHandleClickDayOfWeek(startDay)
              }
            >
              {name}
            </S.DayOfWeek>
          ))}
        </C.WeekDay>
        <C.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);

              if (isBelowToday(day)) {
                return (
                  <S.CalendarDay key={index} type="disable">
                    {day}
                  </S.CalendarDay>
                );
              }

              const dayType = getDayType(day);

              return dayType === 'disable' ? (
                <S.CalendarDay key={index} type={dayType}>
                  {day}
                </S.CalendarDay>
              ) : (
                <S.CalendarDay
                  key={index}
                  type={dayType}
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
