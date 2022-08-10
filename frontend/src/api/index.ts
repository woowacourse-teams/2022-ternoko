import axios from 'axios';

import {
  CoachScheduleRequestBodyType,
  ReservationRequestBodyType,
  UserRequestBodyType,
} from '@/types/domain';

import LocalStorage from '@/localStorage';

const accessToken = LocalStorage.getAccessToken();

if (accessToken) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

export const getCoachesAPI = () => axios.get(`${process.env.SERVER_URL}/api/coaches`);

export const getReservationsAPI = () => axios.get(`${process.env.SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) =>
  axios.get(`${process.env.SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (body: ReservationRequestBodyType) =>
  axios.post(`${process.env.SERVER_URL}/api/reservations`, body);

export const putReservationAPI = (reservationId: number, body: ReservationRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/reservations/${reservationId}`, body);

export const deleteCrewReservationAPI = (reservationId: number) =>
  axios.delete(`${process.env.SERVER_URL}/api/reservations/${reservationId}`);

export const deleteCoachReservationAPI = (reservationId: number) =>
  axios.patch(`${process.env.SERVER_URL}/api/reservations/${reservationId}`);

export const postCoachScheduleAPI = (body: CoachScheduleRequestBodyType) =>
  axios.put(`${process.env.SERVER_URL}/api/calendar/times`, body);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(
    `${process.env.SERVER_URL}/api/calendar/times?coachId=${coachId}&year=${year}&month=${month}`,
  );

export const getCoachReservationAPI = (year: number, month: number) =>
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
