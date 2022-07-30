import { CREW_APPLY_FORM_MIN_LENGTH, NICKNAME_MIN_LENGTH, INTRODUCE_MIN_LENGTH } from '@/constants';

export const isOverApplyFormMinLength = (text: string) => {
  return text.length >= CREW_APPLY_FORM_MIN_LENGTH;
};

export const isOverNicknameMinLength = (text: string) => {
  return text.length >= NICKNAME_MIN_LENGTH;
};

export const isOverIntroduceMinLength = (text: string) => {
  return text.length >= INTRODUCE_MIN_LENGTH;
};
