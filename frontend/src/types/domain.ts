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

export interface InterviewRequestBodyType {
  coachId: number;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
}

export interface UserRequestBodyType {
  nickname: string;
  imageUrl: string;
  introduce?: string;
}

export type InterviewStatus = 'EDITABLE' | 'FIXED' | 'FEEDBACK' | 'COMPLETED' | 'CANCELED';

export interface InterviewType {
  id: number;
  coachNickname: string;
  coachImageUrl: string;
  crewNickname: string;
  crewImageUrl: string;
  interviewStartTime: string;
  interviewEndTime: string;
  status: InterviewStatus;
  interviewQuestions: InterviewQuestionType[];
}

export interface CalendarTime {
  year: number;
  month: number;
  times: string[];
}

export type TimeStatus = 'OPEN' | 'USED';

export type CrewSelectTime = {
  calendarTime: string;
  status: TimeStatus;
};

type CoachAvailableTime = {
  time: string;
  availableDateTimeStatus: TimeStatus;
};

export interface CoachScheduleRequestCalendarTime {
  year: number;
  month: number;
  times: CoachAvailableTime[];
}

export interface CoachScheduleRequestBodyType {
  calendarTimes: CoachScheduleRequestCalendarTime[];
}

export type MemberRole = 'CREW' | 'COACH';

export type MemberExtendedRole = MemberRole | 'ALL';

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
