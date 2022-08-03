export const PAGE = {
  LOGIN: '/login',
  LOGIN_REGISTER: '/login/register',
  SLACK_LOGIN_SERVER:
    'https://ternoko.slack.com/oauth?client_id=3756998338916.3821665111344&scope=&user_scope=openid%2Cemail%2Cprofile&redirect_uri=https%3A%2F%2Fternoko.site%2Fapi%2Flogin&state=&granular_bot_scope=1&single_channel=0&install_redirect=&tracked=1&openid_connect=1&response_type=code&team=',
  CREW_HOME: '/',
  RESERVATION_APPLY: '/reservation/apply',
  RESERVATION_COMPLETE: '/reservation/complete',
  COACH_RESERVATION_CREATE: '/coach/reservation/create',
  COACH_HOME: '/coach/home',
  MY_PAGE: '/mypage',
  OAUTH_REDIRECT: '/api/login',
};

export const CREW_APPLY_FORM_MIN_LENGTH = 10;

export const NICKNAME_MIN_LENGTH = 1;

export const NICKNAME_MAX_LENGTH = 5;

export const INTRODUCE_MIN_LENGTH = 10;

export const ERROR_MESSAGE = {
  ENTER_MINIMUM_APPLY_FORM_LENGTH: `면담 입력폼은 ${CREW_APPLY_FORM_MIN_LENGTH}글자 이상 입력해주세요*`,
  ENTER_IN_RANGE_NICKNAME: `닉네임은 ${NICKNAME_MIN_LENGTH}글자 이상 ${NICKNAME_MAX_LENGTH}글자 이하를 입력해주세요*`,
  ENTER_MINIMUM_INTRODUCE_LENGTH: `한 줄 소개는 ${INTRODUCE_MIN_LENGTH}글자 이상 입력해주세요*`,
};
