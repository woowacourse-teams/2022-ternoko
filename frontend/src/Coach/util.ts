import { dayNamesOfWeek } from '@/Shared/context/CalendarProvider';

import { convertMonthToMonthIndex } from '@/Shared/utils';

import { DayNameOfWeekType, DayOfWeekWithStartDayType, OneWeekDayType } from '@/Types/domain';

export const getFullDateString = (
  year: number,
  month: number,
  day: number | string,
  time: string,
) => `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${time}`;

export const getDayNameOfWeek = (year: number, month: number, day: number): DayNameOfWeekType => {
  return dayNamesOfWeek[new Date(year, convertMonthToMonthIndex(month), day).getDay()];
};

export const generateDayOfWeekWithStartDay = (
  year: number,
  month: number,
): DayOfWeekWithStartDayType[] => {
  const dayNamesOfWeekWithStartDay = dayNamesOfWeek.map(
    (dayNameOfWeek) =>
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
