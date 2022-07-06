import { useState } from 'react';

import * as S from './styled';

const monthNames = [
  'January',
  'February',
  'March',
  'April',
  'May',
  'June',
  'July',
  'August',
  'September',
  'October',
  'November',
  'December',
];

const isLeapYear = (year: number) =>
  (year % 4 === 0 && year % 100 !== 0 && year % 400 !== 0) ||
  (year % 100 === 0 && year % 400 === 0);

const getFebruaryDays = (year: number) => (isLeapYear(year) ? 29 : 28);

const Calendar = () => {
  const currentDate = new Date();

  const [year, setYear] = useState(currentDate.getFullYear());
  const [month, setMonth] = useState(currentDate.getMonth());
  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const firstDay = new Date(month, year, 1).getDay();

  const handleClickMonth = (monthIndex: number) => {
    setMonth(monthIndex);
    setShowMonthPicker(false);
  };

  return (
    <S.Box>
      <S.Header>
        <S.MonthPicker onClick={() => setShowMonthPicker(true)}>{monthNames[month]}</S.MonthPicker>
        <S.YearPicker>
          <S.YearChange onClick={() => setYear((prev) => prev - 1)}>{'<'}</S.YearChange>
          <p>{year}</p>
          <S.YearChange onClick={() => setYear((prev) => prev + 1)}>{'>'}</S.YearChange>
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
        <S.Days>
          {Array.from({ length: daysOfMonth[month] + firstDay }, (_, index) => {
            if (index >= firstDay) {
              const day = index - firstDay + 1;

              if (
                day === currentDate.getDate() &&
                year === currentDate.getFullYear() &&
                month === currentDate.getMonth()
              ) {
                return <S.Day today>{day}</S.Day>;
              }

              return (
                <S.Day key={index}>
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
          <div onClick={() => handleClickMonth(index)}>{monthName}</div>
        ))}
      </S.MonthContainer>
    </S.Box>
  );
};

export default Calendar;
