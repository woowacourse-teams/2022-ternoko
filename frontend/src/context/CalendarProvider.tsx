import React, { createContext, useState, useContext } from 'react';

type CalendarStateType = {
  year: number;
  month: number;
  selectedYear: number;
  selectedMonth: number;
  selectedDay: number;
  showMonthPicker: boolean;
};

type CalendarActionsType = {
  handleClickPrevYear: () => void;
  handleClickNextYear: () => void;
  handleClickMonthPicker: () => void;
  getSetDay: (day: number) => () => void;
  getHandleClickMonth: (monthIndex: number) => () => void;
};

type CalendarUtilsType = {
  daysLength: number;
  dateString: string;
  isToday: (day: number) => boolean;
  isSelectedDate: (day: number) => boolean;
  isOverFirstDay: (index: number) => boolean;
  getDay: (index: number) => number;
};

type CalendarProviderProps = {
  children: React.ReactNode;
};

export const monthNames = [
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

const CalendarStateContext = createContext<CalendarStateType | null>(null);
const CalendarActionsContext = createContext<CalendarActionsType | null>(null);
const CalendarUtilsContext = createContext<CalendarUtilsType | null>(null);

const CalendarProvider = ({ children }: CalendarProviderProps) => {
  const date = new Date();

  const [year, setYear] = useState(date.getFullYear());
  const [month, setMonth] = useState(date.getMonth());

  // 선택한 날짜
  const [selectedYear, setSelectedYear] = useState(year);
  const [selectedMonth, setSelectedMonth] = useState(month);
  const [selectedDay, setSelectedDay] = useState(-1);

  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const firstDay = new Date(`${year}/${month + 1}/1`).getDay();
  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const daysLength = daysOfMonth[month] + firstDay;
  const dateString = `${selectedYear}-${String(selectedMonth + 1).padStart(2, '0')}-${String(
    selectedDay,
  ).padStart(2, '0')}`;

  const isToday = (day: number) => {
    const date = new Date();

    return day === date.getDate() && year === date.getFullYear() && month === date.getMonth();
  };

  const isSelectedDate = (day: number) =>
    year === selectedYear && month === selectedMonth && day === selectedDay;

  const isOverFirstDay = (index: number) => index >= firstDay;

  const getDay = (index: number) => index - firstDay + 1;

  const state = { year, month, selectedYear, selectedMonth, selectedDay, showMonthPicker };

  const actions = {
    handleClickPrevYear() {
      setYear((prev) => prev - 1);
    },
    handleClickNextYear() {
      setYear((prev) => prev + 1);
    },
    handleClickMonthPicker: () => {
      setShowMonthPicker(true);
    },
    getSetDay: (day: number) => () => {
      setSelectedYear(year);
      setSelectedMonth(month);
      setSelectedDay(day);
    },
    getHandleClickMonth: (monthIndex: number) => () => {
      setMonth(monthIndex);
      setShowMonthPicker(false);
    },
  };

  const utils = {
    dateString,
    daysLength,
    isToday,
    isSelectedDate,
    isOverFirstDay,
    getDay,
  };

  return (
    <CalendarStateContext.Provider value={state}>
      <CalendarActionsContext.Provider value={actions}>
        <CalendarUtilsContext.Provider value={utils}>{children}</CalendarUtilsContext.Provider>
      </CalendarActionsContext.Provider>
    </CalendarStateContext.Provider>
  );
};

export const useCalendarState = () => useContext(CalendarStateContext) as CalendarStateType;

export const useCalendarActions = () => useContext(CalendarActionsContext) as CalendarActionsType;

export const useCalendarUtils = () => useContext(CalendarUtilsContext) as CalendarUtilsType;

export default CalendarProvider;
