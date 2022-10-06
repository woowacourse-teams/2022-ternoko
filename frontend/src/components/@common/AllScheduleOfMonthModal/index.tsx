import * as S from './styled';

import Modal from '@/components/@common/Modal';

import { CalendarTime } from '@/types/domain';

import { separateFullDate } from '@/utils';

type AllScheduleOfMonthModalProps = {
  show: boolean;
  display: boolean;
  year: number;
  month: number;
  calendarTime: CalendarTime['times'];
  handleCloseModal: () => void;
};

const getTimesMappedToDay = (calendarTime: CalendarTime['times']) => {
  return Object.entries(
    calendarTime.reduce((timesMappedToDay, timeOfMonth) => {
      const { day, time } = separateFullDate(timeOfMonth);
      const dayAsNumber = Number(day);

      timesMappedToDay[dayAsNumber] = timesMappedToDay[dayAsNumber]
        ? [...timesMappedToDay[dayAsNumber], time]
        : [time];

      return timesMappedToDay;
    }, {} as Record<string, CalendarTime['times']>),
  );
};

const AllScheduleOfMonthModal = ({
  show,
  display,
  year,
  month,
  calendarTime,
  handleCloseModal,
}: AllScheduleOfMonthModalProps) => {
  const timesMappedToDay = getTimesMappedToDay(calendarTime);

  return (
    <Modal
      show={show}
      display={display}
      handleCloseModal={handleCloseModal}
      additionalFrameStyle={S.additionalFrameStyle}
    >
      <S.Box>
        {timesMappedToDay.length === 0 ? (
          <S.EmptyMessage>생성한 면담 스케쥴이 없습니다.🤣</S.EmptyMessage>
        ) : (
          timesMappedToDay.map(([day, times]) => (
            <div key={day}>
              <S.FullDate>
                {year}년 {month}월 {day}일
              </S.FullDate>
              <S.TimeContainer>
                {times.map((time, index) => (
                  <S.Time key={index}>{time}</S.Time>
                ))}
              </S.TimeContainer>
            </div>
          ))
        )}
      </S.Box>
    </Modal>
  );
};

export default AllScheduleOfMonthModal;
