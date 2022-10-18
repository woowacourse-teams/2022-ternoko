import { CalendarTimeType } from '@/Types/domain';

export const convertMonthToMonthIndex = (month: CalendarTimeType['month']) => month - 1;

export const convertMonthIndexToMonth = (monthIndex: number): CalendarTimeType['month'] =>
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
