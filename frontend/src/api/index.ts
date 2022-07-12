import axios from 'axios';
import { ReservationRequestBodyType } from '../types/domain';

const SERVER_URL = 'http://192.168.7.160:8080';

export const getCoachesAPI = () => axios.get(`${SERVER_URL}/api/reservations/coaches`);

export const getReservationsAPI = () => axios.get(`${SERVER_URL}/api/reservations`);

export const getReservationAPI = (id: number) => axios.get(`${SERVER_URL}/api/reservations/${id}`);

export const postReservationAPI = (coachId: number, body: ReservationRequestBodyType) =>
  axios.post(`${SERVER_URL}/api/reservations/coaches/${coachId}`, body);
