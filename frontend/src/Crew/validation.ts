import { CREW_APPLY_FORM_MAX_LENGTH, CREW_APPLY_FORM_MIN_LENGTH } from './constants';

export const isValidApplyFormLength = (text: string) => {
  return text.length >= CREW_APPLY_FORM_MIN_LENGTH && text.length <= CREW_APPLY_FORM_MAX_LENGTH;
};
