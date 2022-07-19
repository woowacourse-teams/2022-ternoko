import axios from 'axios';

import { ReservationRequestBodyType, CoachScheduleRequestBodyType } from '../types/domain';

const SERVER_URL = 'http://15.164.218.98:8080';

export const getCoachesAPI = () => axios.get(`${SERVER_URL}/api/reservations/coaches`);

export const getReservationsAPI = () => axios.get(`${SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) => axios.get(`${SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (coachId: number, body: ReservationRequestBodyType) =>
  axios.post(`${SERVER_URL}/api/reservations/coaches/${coachId}`, body);

export const postCoachScheduleAPI = (coachId: number, body: CoachScheduleRequestBodyType) =>
  axios.put(`${SERVER_URL}/api/coaches/${coachId}/calendar/times`, body);

export const getCoachScheduleAPI = (coachId: number, year: number, month: number) =>
  axios.get(`${SERVER_URL}/api/coaches/${coachId}/calendar/times?year=${year}&month=${month}`);
