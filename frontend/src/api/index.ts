import axios from 'axios';

import { CoachScheduleRequestBodyType, ReservationRequestBodyType } from '@/types/domain';

const SERVER_URL = 'https://ternoko.site';

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
