import axios from 'axios';

import {
  CoachScheduleRequestBodyType,
  ReservationRequestBodyType,
  UserRequestBodyType,
} from '@/types/domain';

import LocalStorage from '@/localStorage';

const SERVER_URL = 'https://dev.ternoko.site';

const accessToken = LocalStorage.getAccessToken();

if (accessToken) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

export const getCoachesAPI = () => axios.get(`${SERVER_URL}/api/coaches`);

export const getReservationsAPI = () => axios.get(`${SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) => axios.get(`${SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (body: ReservationRequestBodyType) =>
  axios.post(`${SERVER_URL}/api/reservations`, body);

export const putReservationAPI = (reservationId: number, body: ReservationRequestBodyType) =>
  axios.put(`${SERVER_URL}/api/reservations/${reservationId}`, body);

export const deleteCrewReservationAPI = (reservationId: number) =>
  axios.delete(`${SERVER_URL}/api/reservations/${reservationId}`);

export const deleteCoachReservationAPI = (reservationId: number) =>
  axios.patch(`${SERVER_URL}/api/reservations/${reservationId}`);

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${SERVER_URL}/api/calendar/times`, body);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/calendar/times?coachId=${coachId}&year=${year}&month=${month}`);

export const getCoachReservationAPI = (year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/schedules?year=${year}&month=${month}`);

export const getUserStatusAPI = (code: string) => axios.get(`${SERVER_URL}/api/login?code=${code}`);

export const getCrewInfoAPI = () => axios.get(`${SERVER_URL}/api/crews/me`);

export const getCoachInfoAPI = () => axios.get(`${SERVER_URL}/api/coaches/me`);

export const patchCrewInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${SERVER_URL}/api/crews/me`, body);

export const patchCoachInfoAPI = (body: UserRequestBodyType) =>
  axios.patch(`${SERVER_URL}/api/coaches/me`, body);

export const getDuplicatedNicknameStatusAPI = (nickname: string) =>
  axios.get(`${SERVER_URL}/api/login/check?nickname=${nickname}`);
