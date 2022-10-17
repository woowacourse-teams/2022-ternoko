import axios from 'axios';

import { CoachScheduleRequestBodyType, UserRequestBodyType } from '@/common/types/domain';

export const deleteCoachInterviewAPI = (interviewId: number, flag: boolean) =>
  axios.patch(`${process.env.SERVER_URL}/api/interviews/${interviewId}?onlyInterview=${flag}`);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(
    `${process.env.SERVER_URL}/api/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/calendar/times`, body);

export const getCoachInfoAPI = () => axios.get(`${process.env.SERVER_URL}/api/coaches/me`);

export const patchCoachInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/coaches/me`, body);
