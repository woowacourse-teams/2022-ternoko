export interface CoachType {
  id: number;
  nickname: string;
  imageUrl: string;
}

interface InterviewQuestionType {
  question: string;
  answer: string;
}

export interface ReservationType {
  id: number;
  imageUrl: string;
  crewNickname: string;
  coachNickname: string;
  interviewStartTime: string;
  interviewEndTime: string;
  interviewQuestions: InterviewQuestionType[];
}

export type ReservationResponseType = Omit<ReservationType, 'imageUrl' | 'coachNickname'>;

export type SelectMode = 'single' | 'multiple';

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

export type DayType = 'default' | 'disable' | 'active';
