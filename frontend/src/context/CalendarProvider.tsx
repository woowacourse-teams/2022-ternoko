import { createContext, useContext, useState } from 'react';

import { SelectMode } from '@/types/domain';

import { convertMonthIndexToMonth, convertMonthToMonthIndex } from '@/utils';

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
  handleClickPrevMonth: () => void;
  handleClickNextMonth: () => void;
  handleClickMonthPicker: () => void;
  removeDate: (day: number) => void;
  addOrSetDateBySelectMode: (day: number) => void;
  getHandleClickMonth: (monthIndex: number) => () => void;
  resetSelectedDates: () => void;
  addSelectedDates: (dates: Date[]) => void;
  removeSelectedDates: (dates: Date[]) => void;
  initializeYearMonth: (year: number, month: number) => void;
};

type CalendarUtilsType = {
  daysLength: number;
  isToday: (day: number) => boolean;
  isBelowToday: (day: number) => boolean;
  isSelectedDate: (day: number) => boolean;
  isOverFirstDay: (index: number) => boolean;
  isSameDate: (date: Date, day: number) => boolean;
  getDay: (index: number) => number;
  getDateStrings: () => string[];
};

export const monthNames = [
  '1월',
  '2월',
  '3월',
  '4월',
  '5월',
  '6월',
  '7월',
  '8월',
  '9월',
  '10월',
  '11월',
  '12월',
];

export const dayNamesOfWeek = ['일', '월', '화', '수', '목', '금', '토'] as const;

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
  const [month, setMonth] = useState(date.getMonth() + 1);

  const [selectedDates, setSelectedDates] = useState<Date[]>([]);

  const [showMonthPicker, setShowMonthPicker] = useState(false);

  const firstDay = new Date(year, convertMonthToMonthIndex(month), 1).getDay();
  const daysOfMonth = [31, getFebruaryDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  const daysLength = daysOfMonth[convertMonthToMonthIndex(month)] + firstDay;

  const isSameDate = (date: Date, day: number) =>
    date.year === year && date.month === month && date.day === day;

  const isToday = (day: number) => {
    const date = new Date();

    return (
      day === date.getDate() &&
      year === date.getFullYear() &&
      convertMonthToMonthIndex(month) === date.getMonth()
    );
  };

  const isBelowToday = (day: number) => {
    const today = new Date().getTime();
    const date = new Date(year, convertMonthToMonthIndex(month), day).getTime();

    return date <= today;
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
    handleClickPrevMonth() {
      setMonth((prev) => {
        if (prev === 1) {
          actions.handleClickPrevYear();

          return 12;
        }

        return prev - 1;
      });
    },
    handleClickNextMonth() {
      setMonth((prev) => {
        if (prev === 12) {
          actions.handleClickNextYear();

          return 1;
        }

        return prev + 1;
      });
    },
    handleClickMonthPicker() {
      setShowMonthPicker(true);
    },
    removeDate(day: number) {
      setSelectedDates((prev) => prev.filter((date) => !isSameDate(date, day)));
    },
    addOrSetDateBySelectMode(day: number) {
      selectMode === 'SINGLE'
        ? setSelectedDates([{ year, month, day }])
        : setSelectedDates((prev) => [...prev, { year, month, day }]);
    },
    getHandleClickMonth: (monthIndex: number) => () => {
      setMonth(convertMonthIndexToMonth(monthIndex));
      setShowMonthPicker(false);
    },
    resetSelectedDates() {
      setSelectedDates([]);
    },
    addSelectedDates(dates: Date[]) {
      setSelectedDates((prev) => [...prev, ...dates]);
    },
    removeSelectedDates(dates: Date[]) {
      setSelectedDates((prev) =>
        prev.filter((selectedDate) =>
          dates.every(
            (date) =>
              selectedDate.year !== date.year ||
              selectedDate.month !== date.month ||
              selectedDate.day !== date.day,
          ),
        ),
      );
    },
    initializeYearMonth(year: number, month: number) {
      setYear(year);
      setMonth(month);
    },
  };

  const utils = {
    daysLength,
    isToday,
    isBelowToday,
    isSelectedDate,
    isOverFirstDay,
    isSameDate,
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
