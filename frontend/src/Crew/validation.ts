import { CREW_APPLY_FORM_MAX_LENGTH, CREW_APPLY_FORM_MIN_LENGTH } from '@/Crew/constants';

export const isValidApplyFormLength = (text: string) => {
  return text.length >= CREW_APPLY_FORM_MIN_LENGTH && text.length <= CREW_APPLY_FORM_MAX_LENGTH;
};
