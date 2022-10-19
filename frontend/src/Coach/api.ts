import axios from 'axios';

import { AvailableTimeType, CoachScheduleRequestCalendarTimeType, CoachType } from '@/Types/domain';

type GetCoachScheduleResponseType = {
  calendarTimes: AvailableTimeType[];
};

type CoachScheduleRequestBodyType = {
  calendarTimes: CoachScheduleRequestCalendarTimeType[];
};

type GetCoachInfoResponseType = CoachType;

type CoachInfoRequestBodyType = {
  nickname: string;
  imageUrl: string;
  introduce: string;
};

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get<GetCoachScheduleResponseType>(
    `${process.env.SERVER_URL}/api/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/calendar/times`, body);

export const deleteCoachInterviewAPI = (interviewId: number, flag: boolean) =>
  axios.patch(`${process.env.SERVER_URL}/api/interviews/${interviewId}?onlyInterview=${flag}`);

export const getCoachInfoAPI = () =>
  axios.get<GetCoachInfoResponseType>(`${process.env.SERVER_URL}/api/coaches/me`);

export const patchCoachInfoAPI = (body: CoachInfoRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/coaches/me`, body);
