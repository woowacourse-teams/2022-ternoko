import {
  CREW_APPLY_FORM_MIN_LENGTH,
  INTRODUCE_MIN_LENGTH,
  NICKNAME_MAX_LENGTH,
  NICKNAME_MIN_LENGTH,
} from '@/constants';

export const isOverApplyFormMinLength = (text: string) => {
  return text.length >= CREW_APPLY_FORM_MIN_LENGTH;
};

export const isValidNicknameLength = (text: string) => {
  return text.length >= NICKNAME_MIN_LENGTH && text.length <= NICKNAME_MAX_LENGTH;
};

export const isOverIntroduceMinLength = (text: string) => {
  return text.length >= INTRODUCE_MIN_LENGTH;
};
