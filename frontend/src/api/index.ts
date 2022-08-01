import axios from 'axios';

import {
  CoachScheduleRequestBodyType,
  ReservationRequestBodyType,
  UserRequestBodyType,
} from '@/types/domain';

const SERVER_URL = 'https://ternoko.site';

const accessToken = localStorage.getItem('accessToken');

if (accessToken) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

export const getCoachesAPI = () => axios.get(`${SERVER_URL}/api/coaches`);

export const getReservationsAPI = () => axios.get(`${SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) => axios.get(`${SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (body: ReservationRequestBodyType) =>
  axios.post(`${SERVER_URL}/api/reservations`, body);

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${SERVER_URL}/api/calendar/times`, body);

export const getCoachScheduleAPI = (year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/calendar/times?year=${year}&month=${month}`);

export const getCoachReservationAPI = (year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/schedules?year=${year}&month=${month}`);

export const getUserStatusAPI = (code: string) => axios.get(`${SERVER_URL}/api/login?code=${code}`);

export const getCrewInfoAPI = () => axios.get(`${SERVER_URL}/api/crews/me`);

export const getCoachInfoAPI = () => axios.get(`${SERVER_URL}/api/coaches/me`);

export const patchCrewInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${SERVER_URL}/api/crews/me`, body);

export const patchCoachInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${SERVER_URL}/api/coach/me`, body);

export const getDuplicatedNicknameStatusAPI = (nickname: string) =>
  axios.get(`${SERVER_URL}/api/login?nickname=${nickname}`);
