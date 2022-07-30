export interface CoachType {
  id: number;
  nickname: string;
  imageUrl: string;
  introduce: string;
}

interface InterviewQuestionType {
  question: string;
  answer: string;
}

export interface ReservationRequestBodyType {
  coachId: number;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
}

type ReservationStatus = 'EDITABLE' | 'FIXED' | 'FEEDBACK' | 'COMPLETED' | 'CANCELED';

export interface ReservationType {
  id: number;
  coachNickname: string;
  imageUrl: string;
  crewNickname: string;
  interviewStartTime: string;
  interviewEndTime: string;
  status: ReservationStatus;
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

export type SelectMode = 'single' | 'multiple';

export type DayType = 'default' | 'disable' | 'active';
