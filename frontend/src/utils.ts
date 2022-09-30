import { dayNamesOfWeek } from '@/context/CalendarProvider';

import { DayOfWeekType, DayOfWeekWithStartDayType, OneWeekDayType } from '@/types/domain';

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
  const date = new Date(Number(year), Number(month) - 1, Number(day), Number(hour), Number(minute));

  return date.getTime() <= new Date().getTime();
};

export const getDayOfWeek = (year: number, month: number, day: number): DayOfWeekType => {
  return dayNamesOfWeek[new Date(year, month, day).getDay()] as any;
};

export const generateDayOfWeeks = (year: number, month: number): DayOfWeekWithStartDayType[] => {
  const result = dayNamesOfWeek.map(
    (dayNameOfWeek: DayOfWeekType) =>
      ({
        name: dayNameOfWeek,
        startDay: 1,
      } as DayOfWeekWithStartDayType),
  );

  for (let day = 1; day <= 7; day++) {
    result[new Date(year, month, day).getDay()].startDay = day as OneWeekDayType;
  }

  return result;
};
