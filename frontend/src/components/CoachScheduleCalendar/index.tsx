import React, { useEffect, useMemo, useRef, useState } from 'react';

import * as S from './styled';

import BabyShowMoreModal from '@/components/@common/BabyShowMoreModal';
import Button from '@/components/@common/Button/styled';
import Calendar from '@/components/@common/Calendar';
import Dimmer from '@/components/@common/Dimmer/styled';

import { dayNamesOfWeek, useCalendarState, useCalendarUtils } from '@/context/CalendarProvider';

import {
  DayNameOfWeekType,
  InterviewStatus,
  InterviewType,
  ModalPositionType,
} from '@/types/domain';

import { getCoachInterviewAPI } from '@/api';
import { getDayNameOfWeek, isOverToday, separateFullDate } from '@/utils';

type ScheduleType = {
  id: number;
  crewNickname: string;
  times: string[];
  status: InterviewStatus;
};

type SchedulesType = { [key: number]: ScheduleType[] };

type CoachScheduleCalendarProps = {
  getHandleClickSchedule: (interviewId: number) => () => void;
  getHandleClickCommentButton: (interviewId: number, status: InterviewStatus) => () => void;
};

const scheduleItemMargin = 4;

const calculateModalPosition = (clickX: number, clickY: number) => {
  const position = { top: clickY, right: 0, bottom: 0, left: clickX };

  if (clickX > innerWidth / 2) {
    position.right = innerWidth - clickX;
    position.left = 0;
  }

  if (clickY > innerHeight / 2) {
    position.bottom = innerHeight - clickY;
    position.top = 0;
  }

  return position;
};

const CoachScheduleCalendar = ({
  getHandleClickSchedule,
  getHandleClickCommentButton,
}: CoachScheduleCalendarProps) => {
  const { year, month } = useCalendarState();
  const { daysLength, isToday, isBelowToday, isOverFirstDay, getDay } = useCalendarUtils();

  const [schedules, setSchedules] = useState<SchedulesType>({});
  const [scheduleViewCount, setScheduleViewCount] = useState(1);
  const [isOpenBabyModal, setIsOpenModal] = useState(false);
  const [babyModalPosition, setBabyModalPosition] = useState<ModalPositionType>({});
  const [babyModalDay, setBabyModalDay] = useState(-1);
  const [babyModalSchedules, setBabyModalSchedules] = useState<React.ReactNode[]>([]);

  const daysRef = useRef<HTMLDivElement>(null);

  const rerenderKey = useMemo(() => Date.now(), [year, month]);

  const getHandleClickShowMore =
    (day: number, schedules: React.ReactNode[]) => (e: React.MouseEvent) => {
      setBabyModalPosition(calculateModalPosition(e.clientX, e.clientY));
      setBabyModalDay(day);
      setBabyModalSchedules(schedules);
      setIsOpenModal(true);
    };

  const handleClickDimmer = () => setIsOpenModal(false);

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

  useEffect(() => {
    if (!daysRef.current) return;

    const havedSchedulesDay = [...daysRef.current.childNodes].find(
      (dayElement: ChildNode) => dayElement.childNodes.length > 1,
    ) as HTMLDivElement;

    if (!havedSchedulesDay) return;

    const header = havedSchedulesDay.childNodes[0] as HTMLDivElement;
    const schedule = havedSchedulesDay.childNodes[1] as HTMLDivElement;

    setScheduleViewCount(
      Math.max(
        0,
        Math.floor(
          (havedSchedulesDay.clientHeight - header.clientHeight) /
            (schedule.clientHeight + scheduleItemMargin),
        ) - 1,
      ),
    );
  }, [schedules]);

  return (
    <S.Box>
      <Calendar>
        <S.WeekDay>
          {dayNamesOfWeek.map((dayNameOfWeek: DayNameOfWeekType) => (
            <div key={dayNameOfWeek}>{dayNameOfWeek}</div>
          ))}
        </S.WeekDay>
        <S.Days key={rerenderKey} ref={daysRef}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);
              const interviews = schedules[day]
                ? schedules[day].map(({ id, crewNickname, times: [startTime, endTime], status }) =>
                    isOverToday(`${year}-${month + 1}-${day} ${endTime}`) ? (
                      <S.Schedule key={id} status="COMMENT" padding={0}>
                        <S.CrewNickname onClick={getHandleClickSchedule(id)}>
                          {crewNickname}
                        </S.CrewNickname>
                        <Button
                          width="48%"
                          padding="0.5rem"
                          onClick={getHandleClickCommentButton(id, status)}
                        >
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
              const dayContent = !schedules[day]
                ? []
                : schedules[day].length > scheduleViewCount
                ? [
                    ...interviews.slice(0, scheduleViewCount),
                    <S.ShowMore
                      key={`show-more-${day}`}
                      onClick={getHandleClickShowMore(day, interviews)}
                    >
                      {schedules[day].length - scheduleViewCount}개 더보기
                    </S.ShowMore>,
                  ]
                : interviews;

              if (isToday(day)) {
                return (
                  <S.CalendarDay key={index} today>
                    <S.Today>{day}</S.Today>
                    {dayContent}
                  </S.CalendarDay>
                );
              }

              if (isBelowToday(day)) {
                return (
                  <S.CalendarDay key={index} type="disable">
                    <S.CalendarDayHeader>{day}</S.CalendarDayHeader>
                    {dayContent}
                  </S.CalendarDay>
                );
              }

              return (
                <S.CalendarDay key={index}>
                  <S.CalendarDayHeader>{day}</S.CalendarDayHeader>
                  {dayContent}
                </S.CalendarDay>
              );
            }

            return <S.CalendarDay key={index} />;
          })}
          {isOpenBabyModal && (
            <>
              <Dimmer onClick={handleClickDimmer} />
              <BabyShowMoreModal
                modalPosition={babyModalPosition}
                dayNameOfWeek={getDayNameOfWeek(year, month, babyModalDay)}
                day={babyModalDay}
              >
                {babyModalSchedules}
              </BabyShowMoreModal>
            </>
          )}
        </S.Days>
      </Calendar>
    </S.Box>
  );
};

export default CoachScheduleCalendar;
