import React, { createContext, useState, useMemo, useContext } from 'react';

type CalendarStateType = {
  year: number;
  month: number;
  day: number;
  showMonthPicker: boolean;
};

type CalendarActionsType = {
  increaseYear: () => void;
  decreaseYear: () => void;
  setShowMonthPicker: (status: boolean) => void;
  setMonth: (month: number) => void;
  getSetDay: (day: number) => () => void;
  getHandleClickMonth: (monthIndex: number) => () => void;
};

type CalendarUtilsType = {
  firstDay: number;
  daysOfMonth: number[];
  daysLength: number;
  isToday: (day: number) => boolean;
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
  const [day, setDay] = useState(-1);
  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const firstDay = new Date(`${year}/${month + 1}/1`).getDay();
  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const daysLength = daysOfMonth[month] + firstDay;

  const isToday = (day: number) => {
    const date = new Date();

    return day === date.getDate() && year === date.getFullYear() && month === date.getMonth();
  };

  const isOverFirstDay = (index: number) => {
    return index >= firstDay;
  };

  const getDay = (index: number) => {
    return index - firstDay + 1;
  };

  const value = { year, month, day, showMonthPicker };

  const actions = useMemo(
    () => ({
      increaseYear() {
        setYear((prev) => prev + 1);
      },
      decreaseYear() {
        setYear((prev) => prev - 1);
      },
      getSetDay: (day: number) => () => setDay(day),
      getHandleClickMonth: (monthIndex: number) => () => {
        setMonth(monthIndex);
        setShowMonthPicker(false);
      },
      setMonth,
      setShowMonthPicker,
    }),
    [],
  );

  const utils = useMemo(
    () => ({
      firstDay,
      daysOfMonth,
      daysLength,
      showMonthPicker,
      isToday,
      isOverFirstDay,
      getDay,
    }),
    [year, month],
  );

  return (
    <CalendarStateContext.Provider value={value}>
      <CalendarActionsContext.Provider value={actions}>
        <CalendarUtilsContext.Provider value={utils}>{children}</CalendarUtilsContext.Provider>
      </CalendarActionsContext.Provider>
    </CalendarStateContext.Provider>
  );
};

export const useCalendarValue = () => useContext(CalendarStateContext) as CalendarStateType;

export const useCalendarActions = () => useContext(CalendarActionsContext) as CalendarActionsType;

export const useCalendarUtils = () => useContext(CalendarUtilsContext) as CalendarUtilsType;

export default CalendarProvider;
