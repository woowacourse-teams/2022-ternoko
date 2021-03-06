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
