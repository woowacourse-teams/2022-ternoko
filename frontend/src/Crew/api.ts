import axios from 'axios';

import { InterviewRequestBodyType, UserRequestBodyType } from '@/Types/domain';

export const getInterviewAPI = (id: number) =>
  axios.get(`${process.env.SERVER_URL}/api/interviews/${id}`);

export const getInterviewsAPI = () => axios.get(`${process.env.SERVER_URL}/api/interviews`);

export const postInterviewAPI = (body: InterviewRequestBodyType) =>
  axios.post(`${process.env.SERVER_URL}/api/interviews`, body);

export const putInterviewAPI = (interviewId: number, body: InterviewRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/interviews/${interviewId}`, body);

export const getCoachesAPI = () => axios.get(`${process.env.SERVER_URL}/api/coaches`);

export const getCoachInterviewAPI = (year: number, month: number) =>
  axios.get(`${process.env.SERVER_URL}/api/schedules?year=${year}&month=${month}`);

export const getCoachScheduleAndUsedScheduleAPI = (
  interviewId: number,
  coachId: number,
  year: number,
  month: number,
) =>
  axios.get(
    `${process.env.SERVER_URL}/api/interviews/${interviewId}/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const getCrewInfoAPI = () => axios.get(`${process.env.SERVER_URL}/api/crews/me`);

export const patchCrewInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/crews/me`, body);
