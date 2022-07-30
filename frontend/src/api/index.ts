import axios from 'axios';

import { CoachScheduleRequestBodyType, ReservationRequestBodyType } from '@/types/domain';

const SERVER_URL = 'https://ternoko.site';

export const getCoachesAPI = () => axios.get(`${SERVER_URL}/api/reservations/coaches`);

export const getReservationsAPI = () => axios.get(`${SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) => axios.get(`${SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (coachId: number, body: ReservationRequestBodyType) =>
  axios.post(`${SERVER_URL}/api/reservations/coaches/${coachId}`, body);

export const postCoachScheduleAPI = (coachId: number, body: CoachScheduleRequestBodyType) =>
  axios.put(`${SERVER_URL}/api/coaches/${coachId}/calendar/times`, body);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/coaches/${coachId}/calendar/times?year=${year}&month=${month}`);

export const getCoachReservationAPI = (coachId: number, year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/coaches/${coachId}/schedules?year=${year}&month=${month}`);
