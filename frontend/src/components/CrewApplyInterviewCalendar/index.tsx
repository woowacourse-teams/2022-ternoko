import { memo, useMemo } from 'react';

import * as S from './styled';

import * as C from '@/components/@common/CalendarStyle/styled';

import {
  dayNamesOfWeek,
  monthNames,
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/context/CalendarProvider';

import { DayType } from '@/types/domain';

export type CrewApplyInterviewCalendarProps = {
  rerenderCondition: number;
  getHandleClickDay: (day: number) => () => void;
  getDayType: (day: number) => DayType;
};

const CrewApplyInterviewCalendar = ({
  rerenderCondition,
  getHandleClickDay,
  getDayType,
}: CrewApplyInterviewCalendarProps) => {
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
          {dayNamesOfWeek.map((dayNameOfWeek) => (
            <div key={dayNameOfWeek}>
              <p>{dayNameOfWeek}</p>
            </div>
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
                <S.CalendarDay key={index} type={dayType} onClick={getHandleClickDay(day)}>
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

export default memo(CrewApplyInterviewCalendar);
