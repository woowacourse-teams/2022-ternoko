import axios from 'axios';

import {
  AvailableTimeType,
  CoachType,
  CrewType,
  InterviewQuestionType,
  InterviewType,
} from '@/Types/domain';

type GetInterviewResponseType = InterviewType;

type GetInterviewsResponseType = InterviewType[];

type GetCoachesResponseType = {
  coaches: CoachType[];
};

type InterviewRequestBodyType = {
  coachId: number;
  availableDateTimeId: number;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
};

type GetCoachInterviewResponseType = {
  calendar: InterviewType[];
};

type GetCrewInfoResponseType = CrewType;

type PatchCrewInfoRequestBodyType = {
  nickname: string;
  imageUrl: string;
};

type GetCoachScheduleAndUsedScheduleResponseType = {
  calendarTimes: AvailableTimeType[];
};

export const getInterviewAPI = (id: number) =>
  axios.get<GetInterviewResponseType>(`${process.env.SERVER_URL}/api/interviews/${id}`);

export const getInterviewsAPI = () =>
  axios.get<GetInterviewsResponseType>(`${process.env.SERVER_URL}/api/interviews`);

export const postInterviewAPI = (body: InterviewRequestBodyType) =>
  axios.post(`${process.env.SERVER_URL}/api/interviews`, body);

export const putInterviewAPI = (interviewId: number, body: InterviewRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/interviews/${interviewId}`, body);

export const getCoachesAPI = () =>
  axios.get<GetCoachesResponseType>(`${process.env.SERVER_URL}/api/coaches`);

export const getCoachInterviewAPI = (year: number, month: number) =>
  axios.get<GetCoachInterviewResponseType>(
    `${process.env.SERVER_URL}/api/schedules?year=${year}&month=${month}`,
  );

export const getCoachScheduleAndUsedScheduleAPI = (
  interviewId: number,
  coachId: number,
  year: number,
  month: number,
) =>
  axios.get<GetCoachScheduleAndUsedScheduleResponseType>(
    `${process.env.SERVER_URL}/api/interviews/${interviewId}/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const getCrewInfoAPI = () =>
  axios.get<GetCrewInfoResponseType>(`${process.env.SERVER_URL}/api/crews/me`);

export const patchCrewInfoAPI = (body: PatchCrewInfoRequestBodyType) =>
  axios.patch(`${process.env.SERVER_URL}/api/crews/me`, body);
