import * as S from './styled';

import Modal from '@/common/components/@common/Modal';
import { CalendarTimeType } from '@/common/types/domain';
import { separateFullDate } from '@/common/utils';

type AllScheduleOfMonthModalProps = {
  show: boolean;
  display: boolean;
  year: CalendarTimeType['year'];
  month: CalendarTimeType['month'];
  calendarTime: CalendarTimeType['times'];
  handleCloseModal: () => void;
};

const getTimesMappedToDay = (calendarTime: CalendarTimeType['times']) => {
  return Object.entries(
    calendarTime.reduce((timesMappedToDay, timeOfMonth) => {
      const { day, time } = separateFullDate(timeOfMonth);
      const dayAsNumber = Number(day);

      timesMappedToDay[dayAsNumber] = timesMappedToDay[dayAsNumber]
        ? [...timesMappedToDay[dayAsNumber], time]
        : [time];

      return timesMappedToDay;
    }, {} as Record<number, CalendarTimeType['times']>),
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
  return (
    <Modal
      show={show}
      display={display}
      handleCloseModal={handleCloseModal}
      additionalFrameStyle={S.additionalFrameStyle}
    >
      <S.Box>
        {calendarTime.length > 0 ? (
          getTimesMappedToDay(calendarTime).map(([day, times]) => (
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
        ) : (
          <S.EmptyMessage>생성한 면담 스케쥴이 없습니다.🤣</S.EmptyMessage>
        )}
      </S.Box>
    </Modal>
  );
};

export default AllScheduleOfMonthModal;
