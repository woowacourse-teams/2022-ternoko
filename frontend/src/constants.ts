export const PAGE = {
  BASE: '/',
  LOGIN: '/login',
  LOGIN_REGISTER: '/login/register',
  SLACK_LOGIN_SERVER: `https://${process.env.SLACK_OAUTH_DOMAIN}/oauth?client_id=${process.env.SLACK_CLIENT_ID}&scope=&user_scope=openid%2Cemail%2Cprofile&redirect_uri=${process.env.SLACK_REDIRECT_URL}&state=&granular_bot_scope=1&single_channel=0&install_redirect=&tracked=1&openid_connect=1&response_type=code&team=`,
  CREW_HOME: '/',
  INTERVIEW_APPLY: '/interview/apply',
  INTERVIEW_COMPLETE: '/interview/complete',
  COACH_INTERVIEW_CREATE: '/coach/interview/create',
  COACH_HOME: '/coach/home',
  MY_PAGE: '/mypage',
  OAUTH_REDIRECT: '/api/login',
  ACCESS_DENY: '/access/deny',
  NOT_FOUND: '*',
};

export const CREW_APPLY_FORM_MIN_LENGTH = 10;

export const CREW_APPLY_FORM_MAX_LENGTH = 1000;

export const COACH_INTRODUCE_MIN_LENGTH = 10;

export const COACH_INTRODUCE_MAX_LENGTH = 100;

export const NICKNAME_MIN_LENGTH = 1;

export const NICKNAME_MAX_LENGTH = 5;

export const COMMENT_MIN_LENGTH = 5;

export const COMMENT_MAX_LENGTH = 25;

export const INITIAL_COACH_ID = -1;

export const ERROR_MESSAGE = {
  ENTER_IN_RANGE_APPLY_FORM_LENGTH: `면담 입력폼은 ${CREW_APPLY_FORM_MIN_LENGTH}글자 이상 ${CREW_APPLY_FORM_MAX_LENGTH}글자 이하를 입력해주세용~`,
  ENTER_IN_RANGE_NICKNAME: `닉네임은 ${NICKNAME_MIN_LENGTH}글자 이상 ${NICKNAME_MAX_LENGTH}글자 이하를 입력해주세용~`,
  ENTER_IN_RANGE_INTRODUCE_LENGTH: `한 줄 소개는 ${COACH_INTRODUCE_MIN_LENGTH}글자 이상 ${COACH_INTRODUCE_MAX_LENGTH}글자 이하를 입력해주세용~`,
  ENTER_IN_RANGE_COMMENT: `코멘트는 ${COMMENT_MIN_LENGTH}글자 이상 ${COMMENT_MAX_LENGTH}글자 이하로 입력해주세용~`,
  CREATE_SCHEDULE: '면담 스케쥴 생성이 실패했어용~',
  ALREADY_INTERVIEWED_TIME: '이미 예약된 시간대입니다. ',
  DUPLICATED_NICKNAME: '닉네임이 중복됐어용~',
};

export const SUCCESS_MESSAGE = {
  CREATE_SCHEDULE: '면담 스케줄 생성이 성공했어용~',
  CREATE_COACH_INFO: '코치 정보가 정상적으로 입력됐어용~',
  CREATE_CREW_INFO: '크루 정보가 정상적으로 입력됐어용~',
  UPDATE_COACH_INFO: '코치 정보가 정상적으로 정보가 수정됐어용~',
  UPDATE_CREW_INFO: '크루 정보가 정상적으로 정보가 수정됐어용~',
  CREATE_INTERVIEW: '면담 신청이 성공적으로 완료됐어용~',
  UPDATE_INTERVIEW: '면담 신청이 성공적으로 수정됐어용~',
  CREW_DELETE_INTERVIEW: '면담이 삭제됐어용... ㅠㅠ',
  COACH_DELETE_INTERVIEW: '면담이 삭제됐어용... ㅠㅠ',
  COACH_DELETE_INTERVIEW_AND_TIME: '면담과 시간이 삭제됐어용... ㅠㅠ',
  CREATE_COMMENT: '코멘트가 정상적으로 입력됐어용~',
  UPDATE_COMMENT: '코멘트가 정삭적으로 수정됐어용~',
  SELECT_COACH: (coachName: string) => `${coachName ?? '코치'} 골랐어용~`,
};

export const CONFIRM_DELETE_MESSAGE = '정말로 삭제하시겠습니까?';
