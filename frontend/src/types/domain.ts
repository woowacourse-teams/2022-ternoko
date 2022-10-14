export interface CoachType {
  id: number;
  name: string;
  nickname: string;
  imageUrl: string;
  introduce: string;
  hasOpenTime: boolean;
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
  | 'COMPLETED'
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
  id: number;
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

export type StringDictionary<T> = {
  [key: string]: T[];
};

export type SelectMode = 'SINGLE' | 'MULTIPLE';

export type DayType = 'default' | 'disable' | 'active';

export type DayNameOfWeekType = '일' | '월' | '화' | '수' | '목' | '금' | '토';

export type OneWeekDayType = 1 | 2 | 3 | 4 | 5 | 6 | 7;

export type DayOfWeekWithStartDayType = {
  name: DayNameOfWeekType;
  startDay: OneWeekDayType;
};
