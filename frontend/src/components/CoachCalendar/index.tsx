import { useEffect, useMemo, useState } from 'react';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import * as C from '@/components/@common/CalendarStyle/styled';

import {
  monthNames,
  useCalendarActions,
  useCalendarState,
  useCalendarUtils,
} from '@/context/CalendarProvider';

import { InterviewStatus, InterviewType } from '@/types/domain';

import { getCoachInterviewAPI } from '@/api';
import { isOverToday, separateFullDate } from '@/utils';

type ScheduleType = {
  id: number;
  crewNickname: string;
  times: string[];
  status: InterviewStatus;
};

type SchedulesType = { [key: number]: ScheduleType[] };

type CoachCalendarProps = {
  getHandleClickSchedule: (interviewId: number) => () => void;
  getHandleClickCommentButton: (interviewId: number, status: InterviewStatus) => () => void;
};

const CoachCalendar = ({
  getHandleClickSchedule,
  getHandleClickCommentButton,
}: CoachCalendarProps) => {
  const { year, month, showMonthPicker } = useCalendarState();
  const {
    handleClickPrevYear,
    handleClickNextYear,
    handleClickPrevMonth,
    handleClickNextMonth,
    handleClickMonthPicker,
    getHandleClickMonth,
  } = useCalendarActions();
  const { daysLength, isToday, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();

  const [schedules, setSchedules] = useState<SchedulesType>({});

  const rerenderKey = useMemo(() => Date.now(), [year, month]);

  useEffect(() => {
    (async () => {
      const response = await getCoachInterviewAPI(year, month + 1);

      const schedules = response.data.calendar.reduce(
        (
          acc: SchedulesType,
          { id, crewNickname, interviewStartTime, interviewEndTime, status }: InterviewType,
        ) => {
          const { day, time: startTime } = separateFullDate(interviewStartTime);
          const { time: endTime } = separateFullDate(interviewEndTime);
          const schedule = { id, crewNickname, times: [startTime, endTime], status };

          acc[Number(day)] = acc[Number(day)] ? [...acc[Number(day)], schedule] : [schedule];

          return acc;
        },
        {},
      );

      setSchedules(schedules);
    })();
  }, [year, month]);

  return (
    <S.Box>
      <C.Header>
        <C.YearPicker>
          <C.DateChange onClick={handleClickPrevYear}>{'<'}</C.DateChange>
          <p>{year}</p>
          <C.DateChange onClick={handleClickNextYear}>{'>'}</C.DateChange>
        </C.YearPicker>
        <C.MonthPicker>
          <C.DateChange onClick={handleClickPrevMonth}>{'<'}</C.DateChange>
          <p onClick={handleClickMonthPicker}>{monthNames[month]}</p>
          <C.DateChange onClick={handleClickNextMonth}>{'>'}</C.DateChange>
        </C.MonthPicker>
      </C.Header>

      <C.Body>
        <C.WeekDay>
          <div>일</div>
          <div>월</div>
          <div>화</div>
          <div>수</div>
          <div>목</div>
          <div>금</div>
          <div>토</div>
        </C.WeekDay>
        <C.Days key={rerenderKey}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);
              const interviews = schedules[day]
                ? schedules[day].map(({ id, crewNickname, times: [startTime, endTime], status }) =>
                    isOverToday(`${year}-${month + 1}-${day} ${endTime}`) ? (
                      <S.Schedule key={id} status="COMMENT">
                        <S.CrewNickname onClick={getHandleClickSchedule(id)}>
                          {crewNickname}
                        </S.CrewNickname>
                        <Button height="4rem" onClick={getHandleClickCommentButton(id, status)}>
                          코멘트
                        </Button>
                      </S.Schedule>
                    ) : (
                      <S.Schedule key={id} status="EDITABLE" onClick={getHandleClickSchedule(id)}>
                        {crewNickname} ({startTime}~{endTime})
                      </S.Schedule>
                    ),
                  )
                : [];

              if (isToday(day)) {
                return (
                  <S.CalendarDay key={index} today>
                    {day}
                    {interviews}
                  </S.CalendarDay>
                );
              }

              if (isBelowToday(day)) {
                return (
                  <S.CalendarDay key={index} type="disable">
                    {day}
                    {interviews}
                  </S.CalendarDay>
                );
              }

              return (
                <S.CalendarDay key={index}>
                  {day}
                  {interviews}
                  <span />
                  <span />
                  <span />
                  <span />
                </S.CalendarDay>
              );
            }

            return <S.CalendarDay key={index} />;
          })}
        </C.Days>
      </C.Body>

      <C.MonthContainer show={showMonthPicker}>
        {monthNames.map((monthName, index) => (
          <div key={index} onClick={getHandleClickMonth(index)}>
            {monthName}
          </div>
        ))}
      </C.MonthContainer>
    </S.Box>
  );
};

export default CoachCalendar;
