import {
  COACH_INTRODUCE_MAX_LENGTH,
  COACH_INTRODUCE_MIN_LENGTH,
  COMMENT_MAX_LENGTH,
  COMMENT_MIN_LENGTH,
  CREW_APPLY_FORM_MAX_LENGTH,
  CREW_APPLY_FORM_MIN_LENGTH,
  NICKNAME_MAX_LENGTH,
  NICKNAME_MIN_LENGTH,
} from '@/constants';

export const isValidApplyFormLength = (text: string) => {
  return text.length >= CREW_APPLY_FORM_MIN_LENGTH && text.length <= CREW_APPLY_FORM_MAX_LENGTH;
};

export const isValidNicknameLength = (text: string) => {
  return text.length >= NICKNAME_MIN_LENGTH && text.length <= NICKNAME_MAX_LENGTH;
};

export const isValidIntroduceLength = (text: string) => {
  return text.length >= COACH_INTRODUCE_MIN_LENGTH && text.length <= COACH_INTRODUCE_MAX_LENGTH;
};

export const isValidCommentLength = (text: string) => {
  return text.length >= COMMENT_MIN_LENGTH && text.length <= COMMENT_MAX_LENGTH;
};
