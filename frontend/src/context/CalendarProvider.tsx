import React, { createContext, useState, useContext } from 'react';

import { SelectMode } from '../types/domain';

type CalendarProviderProps = {
  selectMode: SelectMode;
  children: React.ReactNode;
};

type Date = {
  year: number;
  month: number;
  day: number;
};

type CalendarStateType = {
  year: number;
  month: number;
  selectedDates: Date[];
  showMonthPicker: boolean;
};

type CalendarActionsType = {
  handleClickPrevYear: () => void;
  handleClickNextYear: () => void;
  handleClickMonthPicker: () => void;
  setDay: (day: number) => void;
  getHandleClickMonth: (monthIndex: number) => () => void;
  resetSelectedDates: () => void;
};

type CalendarUtilsType = {
  daysLength: number;
  isToday: (day: number) => boolean;
  isBeforeToday: (day: number) => boolean;
  isSelectedDate: (day: number) => boolean;
  isOverFirstDay: (index: number) => boolean;
  getDay: (index: number) => number;
  getDateStrings: () => string[];
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

const CalendarProvider = ({ selectMode, children }: CalendarProviderProps) => {
  const date = new Date();

  const [year, setYear] = useState(date.getFullYear());
  const [month, setMonth] = useState(date.getMonth());

  const [selectedDates, setSelectedDates] = useState<Date[]>([]);

  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const firstDay = new Date(`${year}/${month + 1}/1`).getDay();
  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const daysLength = daysOfMonth[month] + firstDay;

  const isSameDate = (date: Date, day: number) =>
    date.year === year && date.month === month + 1 && date.day === day;

  const isToday = (day: number) => {
    const date = new Date();

    return day === date.getDate() && year === date.getFullYear() && month === date.getMonth();
  };

  const isBeforeToday = (day: number) => {
    const today = new Date().getTime();
    const date = new Date(`${year}-${month + 1}-${day}`).getTime();

    return date < today;
  };

  const isSelectedDate = (day: number) =>
    selectedDates.some((selectedDate) => isSameDate(selectedDate, day));

  const isOverFirstDay = (index: number) => index >= firstDay;

  const getDay = (index: number) => index - firstDay + 1;

  const getDateStrings = () =>
    selectedDates.map(
      ({ year, month, day }) =>
        `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`,
    );

  const state = { year, month, selectedDates, showMonthPicker };

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
    setDay: (day: number) => {
      if (isSelectedDate(day)) {
        setSelectedDates((prev) => prev.filter((date) => !isSameDate(date, day)));

        return;
      }

      selectMode === 'single'
        ? setSelectedDates([{ year, month: month + 1, day }])
        : setSelectedDates((prev) => [...prev, { year, month: month + 1, day }]);
    },
    getHandleClickMonth: (monthIndex: number) => () => {
      setMonth(monthIndex);
      setShowMonthPicker(false);
    },
    resetSelectedDates() {
      setSelectedDates([]);
    },
  };

  const utils = {
    daysLength,
    isToday,
    isBeforeToday,
    isSelectedDate,
    isOverFirstDay,
    getDay,
    getDateStrings,
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
