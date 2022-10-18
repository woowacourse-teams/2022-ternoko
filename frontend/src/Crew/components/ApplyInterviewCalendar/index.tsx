import { memo, useMemo } from 'react';

import * as S from './styled';

import Calendar from '@/Shared/components/Calendar';
import * as C from '@/Shared/components/CrewAndCoachCalendarStyle/styled';

import {
  dayNamesOfWeek,
  useCalendarState,
  useCalendarUtils,
} from '@/Shared/context/CalendarProvider';

import { DayType } from '@/Types/domain';

export type ApplyInterviewCalendarProps = {
  rerenderCondition: number;
  getHandleClickDay: (day: number) => () => void;
  getDayType: (day: number) => DayType;
};

const ApplyInterviewCalendar = ({
  rerenderCondition,
  getHandleClickDay,
  getDayType,
}: ApplyInterviewCalendarProps) => {
  const { year, month } = useCalendarState();
  const { daysLength, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month, rerenderCondition]);

  return (
    <C.Box>
      <Calendar>
        <S.WeekDay>
          {dayNamesOfWeek.map((dayNameOfWeek) => (
            <div key={dayNameOfWeek}>
              <p>{dayNameOfWeek}</p>
            </div>
          ))}
        </S.WeekDay>
        <S.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);

              if (isBelowToday(day)) {
                return (
                  <C.CalendarDay key={index} type="disable">
                    {day}
                  </C.CalendarDay>
                );
              }

              const dayType = getDayType(day);

              return dayType === 'disable' ? (
                <C.CalendarDay key={index} type={dayType}>
                  {day}
                </C.CalendarDay>
              ) : (
                <C.CalendarDay key={index} type={dayType} onClick={getHandleClickDay(day)}>
                  {day}
                  <span />
                  <span />
                  <span />
                  <span />
                </C.CalendarDay>
              );
            }

            return <C.CalendarDay key={index} />;
          })}
        </S.Days>
      </Calendar>
    </C.Box>
  );
};

export default memo(ApplyInterviewCalendar);
