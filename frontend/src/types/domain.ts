export interface CoachType {
  id: number;
  name: string;
  nickname: string;
  imageUrl: string;
  introduce: string;
}

export type CrewType = Omit<CoachType, 'introduce'>;

interface InterviewQuestionType {
  question: string;
  answer: string;
}

export interface ReservationRequestBodyType {
  coachId: number;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
}

export interface UserRequestBodyType {
  nickname: string;
  imageUrl: string;
  introduce?: string;
}

export type ReservationStatus = 'EDITABLE' | 'FIXED' | 'FEEDBACK' | 'COMPLETED' | 'CANCELED';

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

export type MemberRole = 'CREW' | 'COACH';

export interface UserStatusType {
  accessToken: string;
  hasNickname: boolean;
  memberRole: MemberRole;
}

export interface DuplicatedNicknameStatusType {
  exists: boolean;
}

export type StringDictionary = {
  [key: string]: string[];
};

export type SelectMode = 'single' | 'multiple';

export type DayType = 'default' | 'disable' | 'active';
