// FIXME check this type
// export type UserType = {
//   id: number;
//   name: string;
//   nickname: string;
//   imageUrl: string;
//   introduce?: string;
//   hasOpenTime: boolean;
// };

export type CoachType = {
  id: number;
  name: string;
  nickname: string;
  imageUrl: string;
  introduce: string;
  hasOpenTime: boolean;
};

export type CrewType = Omit<CoachType, 'introduce'>;

type InterviewQuestionType = {
  question: string;
  answer: string;
};

export type InterviewRequestBodyType = {
  coachId: number;
  interviewDatetime: string;
  interviewQuestions: InterviewQuestionType[];
};

export type UserRequestBodyType = {
  nickname: string;
  imageUrl: string;
  introduce?: string;
};

export type InterviewStatusType =
  | 'EDITABLE'
  | 'FIXED'
  | 'COMMENT'
  | 'COMPLETED'
  | 'CREW_COMPLETED'
  | 'COACH_COMPLETED'
  | 'CANCELED';

export type InterviewType = {
  id: number;
  coachNickname: string;
  coachImageUrl: string;
  crewNickname: string;
  crewImageUrl: string;
  interviewStartTime: string;
  interviewEndTime: string;
  status: InterviewStatusType;
  interviewQuestions: InterviewQuestionType[];
};

export type DateType = {
  year: number;
  month: number;
  day: number;
};

export type CalendarTimeType = {
  year: number;
  month: number;
  times: string[];
};

export type TimeStatusType = 'OPEN' | 'USED';

export type CrewSelectTimeType = {
  id: number;
  calendarTime: string;
  status: TimeStatusType;
};

type CoachAvailableTimeType = {
  time: string;
  availableDateTimeStatus: TimeStatusType;
};

export type CoachScheduleRequestCalendarTimeType = {
  year: number;
  month: number;
  times: CoachAvailableTimeType[];
};

export type CoachScheduleRequestBodyType = {
  calendarTimes: CoachScheduleRequestCalendarTimeType[];
};

export type CommentRequestBodyType = {
  comment: string;
};

export type CommentType = {
  role: MemberRoleType;
  commentId: number;
  comment: string;
};

export type MemberRoleType = 'CREW' | 'COACH';

export type MemberExtendedRoleType = MemberRoleType | 'ALL';

export type UserStatusType = {
  accessToken: string;
  hasNickname: boolean;
  memberRole: MemberRoleType;
};

export type DuplicatedNicknameStatusType = {
  exists: boolean;
};

export type StringDictionaryType<T> = {
  [key: string]: T[];
};

export type SelectModeType = 'SINGLE' | 'MULTIPLE';

export type DayType = 'default' | 'disable' | 'active';

export type DayNameOfWeekType = '일' | '월' | '화' | '수' | '목' | '금' | '토';

export type OneWeekDayType = 1 | 2 | 3 | 4 | 5 | 6 | 7;

export type DayOfWeekWithStartDayType = {
  name: DayNameOfWeekType;
  startDay: OneWeekDayType;
};
