export interface CoachType {
  id: number;
  nickname: string;
  imageUrl: string;
}

export interface ReservationType {
  id: number;
  imageUrl: string;
  crewNickname: string;
  coachNickname: string;
  interviewStartTime: string;
  interviewEndTime: string;
}

export type ReservationResponseType = Omit<ReservationType, 'imageUrl' | 'coachNickname'>;

export type SelectMode = 'single' | 'multiple';

interface InterviewQuestionType {
  question: string;
  answer: string;
}

export interface ReservationRequestBodyType {
  crewNickname: string;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
}

export interface CalendarTime {
  year: number;
  month: number;
  times: string[];
}

export interface CoachScheduleRequestBodyType {
  calendarTimes: CalendarTime[];
}

export type StringDictionary = {
  [key: string]: string[];
};
