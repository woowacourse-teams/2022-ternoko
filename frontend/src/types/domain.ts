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

export type InterviewStatus =
  | 'EDITABLE'
  | 'FIXED'
  | 'COMMENT'
  | 'COMPLETE'
  | 'CREW_COMPLETED'
  | 'COACH_COMPLETED'
  | 'CANCELED';

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

export interface CrewSelectTime {
  calendarTime: string;
  status: TimeStatus;
}

interface CoachAvailableTime {
  time: string;
  availableDateTimeStatus: TimeStatus;
}

export interface CoachScheduleRequestCalendarTime {
  year: number;
  month: number;
  times: CoachAvailableTime[];
}

export interface CoachScheduleRequestBodyType {
  calendarTimes: CoachScheduleRequestCalendarTime[];
}

export interface CommentRequestBodyType {
  comment: string;
}

export interface CommentType {
  role: MemberRole;
  commentId: number;
  comment: string;
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

export type SelectMode = 'SINGLE' | 'MULTIPLE';

export type DayType = 'default' | 'disable' | 'active';
