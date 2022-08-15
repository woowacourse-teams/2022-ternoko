export const PAGE = {
  BASE: '/',
  LOGIN: '/login',
  LOGIN_REGISTER: '/login/register',
  SLACK_LOGIN_SERVER: `https://ternoko.slack.com/oauth?client_id=3756998338916.3821665111344&scope=&user_scope=openid%2Cemail%2Cprofile&redirect_uri=${process.env.SLACK_REDIRECT_URL}&state=&granular_bot_scope=1&single_channel=0&install_redirect=&tracked=1&openid_connect=1&response_type=code&team=`,
  CREW_HOME: '/',
  RESERVATION_APPLY: '/interview/apply',
  RESERVATION_COMPLETE: '/interview/complete',
  COACH_RESERVATION_CREATE: '/coach/interview/create',
  COACH_HOME: '/coach/home',
  MY_PAGE: '/mypage',
  OAUTH_REDIRECT: '/api/login',
  ACCESS_DENY: '/access/deny',
};

export const CREW_APPLY_FORM_MIN_LENGTH = 10;

export const CREW_APPLY_FORM_MAX_LENGTH = 1000;

export const COACH_INTRODUCE_MIN_LENGTH = 10;

export const COACH_INTRODUCE_MAX_LENGTH = 100;

export const NICKNAME_MIN_LENGTH = 1;

export const NICKNAME_MAX_LENGTH = 5;

export const INITIAL_COACH_ID = -1;

export const ERROR_MESSAGE = {
  ENTER_IN_RANGE_APPLY_FORM_LENGTH: `면담 입력폼은 ${CREW_APPLY_FORM_MIN_LENGTH}글자 이상 ${CREW_APPLY_FORM_MAX_LENGTH}글자 이하를 입력해주세용~`,
  ENTER_IN_RANGE_NICKNAME: `닉네임은 ${NICKNAME_MIN_LENGTH}글자 이상 ${NICKNAME_MAX_LENGTH}글자 이하를 입력해주세용~`,
  ENTER_IN_RANGE_INTRODUCE_LENGTH: `한 줄 소개는 ${COACH_INTRODUCE_MIN_LENGTH}글자 이상 ${COACH_INTRODUCE_MAX_LENGTH}글자 이하를 입력해주세용~`,
  CREATE_SCHEDULE: '면담 스케쥴 생성이 실패했어용~',
  DUPLICATED_NICKNAME: '닉네임이 중복됐어용~',
};

export const SUCCESS_MESSAGE = {
  CREATE_SCHEDULE: '면담 스케줄 생성이 성공했어용~',
  CREATE_COACH_INFO: '코치 정보가 정상적으로 입력됐어용~',
  CREATE_CREW_INFO: '코치 정보가 정상적으로 입력됐어용~',
  UPDATED_COACH_INFO: '코치 정보가 정상적으로 정보가 수정됐어용~',
  UPDATED_CREW_INFO: '크루 정보가 정상적으로 정보가 수정됐어용~',
  CREATE_RESERVATION: '면담 신청이 성공적으로 완료됐어용~',
  UPDATE_RESERVATION: '면담 신청이 성공적으로 수정됐어용~',
  CREW_DELETE_RESERVATION: '면담이 삭제됐어용... ㅠㅠ',
  COACH_DELETE_RESERVATION: '면담이 취소됐어용... ㅠㅠ',
  SELECT_COACH: (coachName: string) => `${coachName ?? '코치'} 골랐어용~`,
};
