import { memo } from 'react';

import * as S from './styled';

import {
  useCalendarActions,
  useCalendarValue,
  useCalendarUtils,
  monthNames,
} from '../../context/CalendarProvider';

export type CalendarProps = {
  rerenderKey?: number;
};

const Calendar = ({ rerenderKey }: CalendarProps) => {
  const { year, month, day: currentDay, showMonthPicker } = useCalendarValue();
  const { increaseYear, decreaseYear, getSetDay, setShowMonthPicker, getHandleClickMonth } =
    useCalendarActions();
  const { daysLength, isToday, isOverFirstDay, getDay } = useCalendarUtils();

  return (
    <S.Box>
      <S.Header>
        <S.MonthPicker onClick={() => setShowMonthPicker(true)}>{monthNames[month]}</S.MonthPicker>
        <S.YearPicker>
          <S.YearChange onClick={decreaseYear}>{'<'}</S.YearChange>
          <p>{year}</p>
          <S.YearChange onClick={increaseYear}>{'>'}</S.YearChange>
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
                  <S.Day key={index} active={currentDay === day} today onClick={getSetDay(day)}>
                    {day}
                  </S.Day>
                );
              }

              return (
                <S.Day key={index} active={currentDay === day} onClick={getSetDay(day)}>
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
