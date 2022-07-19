import { useMemo, memo } from 'react';

import * as S from './styled';

import {
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
  monthNames,
} from '../../context/CalendarProvider';

export type CalendarProps = {
  rerenderCondition?: number;
  getHandleClickDay: (day: number) => () => void;
  isActiveDay: (day: number) => boolean;
};

const Calendar = ({ rerenderCondition, getHandleClickDay, isActiveDay }: CalendarProps) => {
  const { year, month, showMonthPicker } = useCalendarState();
  const { handleClickPrevYear, handleClickNextYear, handleClickMonthPicker, getHandleClickMonth } =
    useCalendarActions();
  const { daysLength, isToday, isBeforeToday, isOverFirstDay, getDay } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month, rerenderCondition]);

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

              if (isToday(day)) {
                return (
                  <S.Day
                    key={index}
                    active={isActiveDay(day)}
                    today
                    onClick={getHandleClickDay(day)}
                  >
                    {day}
                  </S.Day>
                );
              }

              if (isBeforeToday(day)) {
                return (
                  <S.Day key={index} before onClick={getHandleClickDay(day)}>
                    {day}
                  </S.Day>
                );
              }

              return (
                <S.Day key={index} active={isActiveDay(day)} onClick={getHandleClickDay(day)}>
                  {day}
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

export default memo(Calendar);
