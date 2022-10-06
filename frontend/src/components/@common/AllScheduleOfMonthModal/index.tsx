import * as S from './styled';

import Modal from '@/components/@common/Modal';

import { CalendarTime, StringDictionary } from '@/types/domain';

import { separateFullDate } from '@/utils';

type AllScheduleOfMonthModalProps = {
  show: boolean;
  display: boolean;
  calendarTime: CalendarTime;
  handleCloseModal: () => void;
};

type TrimCalendarTimeType = {
  year: number;
  month: number;
  day: number;
  times: string[];
};

const trimCalendarTime = (calendarTime: CalendarTime) => {
  let index = -1;

  return calendarTime.times.reduce((trimmedCalendarTime, fullDate) => {
    const { year, month, day, time } = separateFullDate(fullDate);
    const yearAsNumber = Number(year);
    const monthAsNumber = Number(month);
    const dayAsNumber = Number(day);

    if (index !== -1 && trimmedCalendarTime[index].day === dayAsNumber) {
      trimmedCalendarTime[index].times = [...trimmedCalendarTime[index].times, time];
    } else {
      trimmedCalendarTime[++index] = {
        year: yearAsNumber,
        month: monthAsNumber,
        day: dayAsNumber,
        times: [time],
      };
    }

    return trimmedCalendarTime;
  }, [] as TrimCalendarTimeType[]);
};

const AllScheduleOfMonthModal = ({
  show,
  display,
  calendarTime,
  handleCloseModal,
}: AllScheduleOfMonthModalProps) => {
  const trimmedCalendarTime = calendarTime ? trimCalendarTime(calendarTime) : [];

  return (
    <Modal
      show={show}
      display={display}
      handleCloseModal={handleCloseModal}
      additionalFrameStyle={S.additionalFrameStyle}
    >
      <S.Box>
        {trimmedCalendarTime.length === 0 && (
          <S.EmptyMessage>생성한 면담 스케쥴이 없습니다.🤣</S.EmptyMessage>
        )}
        {trimmedCalendarTime.length !== 0 &&
          trimmedCalendarTime.map(({ year, month, day, times }) => (
            <div>
              <S.FullDate>
                {year}년 {month}월 {day}일
              </S.FullDate>
              <S.TimeContainer>
                {times.map((time) => (
                  <S.Time>{time}</S.Time>
                ))}
              </S.TimeContainer>
            </div>
          ))}
      </S.Box>
    </Modal>
  );
};

export default AllScheduleOfMonthModal;
