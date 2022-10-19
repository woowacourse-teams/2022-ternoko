import { COACH_INTRODUCE_MAX_LENGTH, COACH_INTRODUCE_MIN_LENGTH } from '@/Coach/constants';

import { CREW_APPLY_FORM_MAX_LENGTH, CREW_APPLY_FORM_MIN_LENGTH } from '@/Crew/constants';

import {
  COMMENT_MAX_LENGTH,
  COMMENT_MIN_LENGTH,
  NICKNAME_MAX_LENGTH,
  NICKNAME_MIN_LENGTH,
} from '@/Shared/constants';

export const ERROR_MESSAGE = {
  ENTER_IN_RANGE_APPLY_FORM_LENGTH: `면담 입력폼은 ${CREW_APPLY_FORM_MIN_LENGTH}글자 이상 ${CREW_APPLY_FORM_MAX_LENGTH}글자 이하를 입력해주세요.`,
  ENTER_IN_RANGE_NICKNAME: `닉네임은 ${NICKNAME_MIN_LENGTH}글자 이상 ${NICKNAME_MAX_LENGTH}글자 이하를 입력해주세요.`,
  ENTER_IN_RANGE_INTRODUCE_LENGTH: `한 줄 소개는 ${COACH_INTRODUCE_MIN_LENGTH}글자 이상 ${COACH_INTRODUCE_MAX_LENGTH}글자 이하를 입력해주세요.`,
  ENTER_IN_RANGE_COMMENT: `코멘트는 ${COMMENT_MIN_LENGTH}글자 이상 ${COMMENT_MAX_LENGTH}글자 이하로 입력해주세요.`,
  CREATE_SCHEDULE: '면담 스케쥴 생성이 실패했어요.',
  CHECK_DAY_AND_TIME: '날짜 및 시간을 다시 확인해주세요.',
  DUPLICATED_NICKNAME: '닉네임이 중복됐어요.',
} as const;

export const SUCCESS_MESSAGE = {
  CREW_DELETE_INTERVIEW: '면담이 삭제됐어요...ㅠㅠ',
  COACH_DELETE_INTERVIEW: '면담이 삭제됐어요... ㅠㅠ',
  CREATE_COMMENT: '코멘트가 정상적으로 입력됐어요.',
  UPDATE_COMMENT: '코멘트가 정상적으로 수정됐어요.',
  CREATE_SCHEDULE: '면담 스케줄 생성이 성공했어요.',
  CREATE_COACH_INFO: '코치 정보가 정상적으로 입력됐어요.',
  CREATE_CREW_INFO: '크루 정보가 정상적으로 입력됐어요.',
  UPDATE_COACH_INFO: '코치 정보가 정상적으로 정보가 수정됐어요.',
  UPDATE_CREW_INFO: '크루 정보가 정상적으로 정보가 수정됐어요.',
  CREATE_INTERVIEW: '면담 신청이 성공적으로 완료됐어요.',
  UPDATE_INTERVIEW: '면담 신청이 성공적으로 수정됐어요.',
  COACH_DELETE_INTERVIEW_AND_TIME: '면담과 시간이 삭제됐어요... ㅠㅠ',
} as const;

export const CONFIRM_DELETE_MESSAGE = '정말로 삭제하시겠습니까?';

export const EMPTY_SCREEN_MESSAGE = {
  EMPTY_DOING_INTERVIEW: '진행중 면담이 없습니다.',
  EMPTY_DOME_INTERVIEW: '완료한 면담이 없습니다.',
} as const;
