import { useState } from 'react';

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

const useCalendar = () => {
  const currentDate = new Date();

  const [year, setYear] = useState(currentDate.getFullYear());
  const [month, setMonth] = useState(currentDate.getMonth());
  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const firstDay = new Date(`${year}/${month + 1}/1`).getDay();
  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const daysLength = daysOfMonth[month] + firstDay;

  const isToday = (day: number) => {
    return (
      day === currentDate.getDate() &&
      year === currentDate.getFullYear() &&
      month === currentDate.getMonth()
    );
  };

  const isOverFirstDay = (index: number) => {
    return index >= firstDay;
  };

  const getDay = (index: number) => {
    return index - firstDay + 1;
  };

  const getHandleClickMonth = (monthIndex: number) => () => {
    setMonth(monthIndex);
    setShowMonthPicker(false);
  };

  const handleClickPrevYear = () => {
    setYear((prev) => prev - 1);
  };

  const handleClickNextYear = () => {
    setYear((prev) => prev + 1);
  };

  const handleClickMonthPicker = () => {
    setShowMonthPicker(true);
  };

  return {
    daysLength,
    monthNames,
    month,
    year,
    showMonthPicker,
    getHandleClickMonth,
    handleClickPrevYear,
    handleClickNextYear,
    handleClickMonthPicker,
    getDay,
    isToday,
    isOverFirstDay,
  };
};

export default useCalendar;
