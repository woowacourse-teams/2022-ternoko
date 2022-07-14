const separateFullDate = (fullDate: string) => {
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
