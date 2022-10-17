import { dayNamesOfWeek } from '@/context/CalendarProvider';

import {
  CalendarTime,
  DayNameOfWeekType,
  DayOfWeekWithStartDayType,
  OneWeekDayType,
} from '@/types/domain';

export const convertMonthToMonthIndex = (month: CalendarTime['month']) => month - 1;

export const convertMonthIndexToMonth = (monthIndex: number): CalendarTime['month'] =>
  monthIndex + 1;

export const separateFullDate = (fullDate: string) => {
  const [date, time] = fullDate.split(' ');
  const [year, month, day] = date.split('-');

  return { year, month, day, time };
};

export const getDateString = (fullDate: string) => {
  const { year, month, day } = separateFullDate(fullDate);

  return `${year}년 ${month}월 ${day}일`;
};

export const getTimeString = (fullDate: string) => {
  const { time } = separateFullDate(fullDate);

  return time;
};

export const getFullDateString = (
  year: number,
  month: number,
  day: number | string,
  time: string,
) => `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${time}`;

export const isOverToday = (fullDate: string) => {
  const { year, month, day, time } = separateFullDate(fullDate);
  const [hour, minute] = time.split(':');
  const date = new Date(
    Number(year),
    convertMonthToMonthIndex(Number(month)),
    Number(day),
    Number(hour),
    Number(minute),
  );

  return date.getTime() <= new Date().getTime();
};

export const getDayNameOfWeek = (year: number, month: number, day: number): DayNameOfWeekType => {
  return dayNamesOfWeek[new Date(year, convertMonthToMonthIndex(month), day).getDay()] as any;
};

export const generateDayOfWeekWithStartDay = (
  year: number,
  month: number,
): DayOfWeekWithStartDayType[] => {
  const dayNamesOfWeekWithStartDay = dayNamesOfWeek.map(
    (dayNameOfWeek: DayNameOfWeekType) =>
      ({
        name: dayNameOfWeek,
        startDay: 1,
      } as DayOfWeekWithStartDayType),
  );
  const monthIndex = convertMonthToMonthIndex(month);

  for (let day = 1; day <= 7; day++) {
    dayNamesOfWeekWithStartDay[new Date(year, monthIndex, day).getDay()].startDay =
      day as OneWeekDayType;
  }

  return dayNamesOfWeekWithStartDay;
};

export const isSunDay = (year: number, month: number, day: number) =>
  new Date(year, convertMonthToMonthIndex(month), day).getDay() === 0;
