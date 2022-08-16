import axios from 'axios';

import {
  CoachScheduleRequestBodyType,
  CommentRequestBodyType,
  InterviewRequestBodyType,
  MemberExtendedRole,
  UserRequestBodyType,
} from '@/types/domain';

import LocalStorage from '@/localStorage';

const accessToken = LocalStorage.getAccessToken();

if (accessToken) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

export const getCoachesAPI = () => axios.get(`${process.env.SERVER_URL}/api/coaches`);

export const getInterviewsAPI = () => axios.get(`${process.env.SERVER_URL}/api/interviews`);

export const getInterviewAPI = (id: number) =>
  axios.get(`${process.env.SERVER_URL}/api/interviews/${id}`);

export const postInterviewAPI = (body: InterviewRequestBodyType) =>
  axios.post(`${process.env.SERVER_URL}/api/interviews`, body);

export const putInterviewAPI = (interviewId: number, body: InterviewRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/interviews/${interviewId}`, body);

export const deleteCrewInterviewAPI = (interviewId: number) =>
  axios.delete(`${process.env.SERVER_URL}/api/interviews/${interviewId}`);

export const deleteCoachInterviewAPI = (interviewId: number, flag: boolean) =>
  axios.patch(`${process.env.SERVER_URL}/api/interviews/${interviewId}?onlyInterview=${flag}`);

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/calendar/times`, body);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(
    `${process.env.SERVER_URL}/api/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const getCoachInterviewAPI = (year: number, month: number) =>
  axios.get(`${process.env.SERVER_URL}/api/schedules?year=${year}&month=${month}`);

export const getUserStatusAPI = (code: string, redirectUrl: string) =>
  axios.get(`${process.env.SERVER_URL}/api/login?code=${code}&redirectUrl=${redirectUrl}`);

export const getCrewInfoAPI = () => axios.get(`${process.env.SERVER_URL}/api/crews/me`);

export const getCoachInfoAPI = () => axios.get(`${process.env.SERVER_URL}/api/coaches/me`);

export const patchCrewInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/crews/me`, body);

export const patchCoachInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/coaches/me`, body);

export const getDuplicatedNicknameStatusAPI = (nickname: string) =>
  axios.get(`${process.env.SERVER_URL}/api/login/check?nickname=${nickname}`);

export const validateAccessTokenAPI = (type: MemberExtendedRole) =>
  axios.get(`${process.env.SERVER_URL}/api/login/valid?type=${type}`);

export const postCommentAPI = (interviewId: number, body: CommentRequestBodyType) =>
  axios.post(`${process.env.SERVER_URL}/api/interviews/${interviewId}/comments`, body);

export const getCommentAPI = (interviewId: number) =>
  axios.get(`${process.env.SERVER_URL}/api/interviews/${interviewId}/comments`);

export const putCommentAPI = (commentId: number) =>
  axios.put(`${process.env.SERVER_URL}/api/interview/{interviewId}/comment/${commentId}`);
