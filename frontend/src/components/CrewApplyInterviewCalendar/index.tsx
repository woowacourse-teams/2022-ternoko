import { memo, useMemo } from 'react';

import Calendar from '@/components/@common/Calendar';
import * as C from '@/components/@common/CommonCrewAndCoachCalendarStyle/styled';

import { dayNamesOfWeek, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';

import { DayType } from '@/types/domain';

export type CrewApplyInterviewCalendarProps = {
  rerenderCondition: number;
  getHandleClickDay: (day: number) => () => void;
  getDayType: (day: number) => DayType;
};

const CrewApplyInterviewCalendar = ({
  rerenderCondition,
  getHandleClickDay,
  getDayType,
}: CrewApplyInterviewCalendarProps) => {
  const { year, month } = useCalendarState();
  const { daysLength, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();
  const rerenderKey = useMemo(() => Date.now(), [year, month, rerenderCondition]);

  return (
    <C.Box>
      <Calendar>
        <C.WeekDay>
          {dayNamesOfWeek.map((dayNameOfWeek) => (
            <div key={dayNameOfWeek}>
              <p>{dayNameOfWeek}</p>
            </div>
          ))}
        </C.WeekDay>
        <C.Days key={rerenderKey}>
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
        </C.Days>
      </Calendar>
    </C.Box>
  );
};

export default memo(CrewApplyInterviewCalendar);
