import { COACH_INTRODUCE_MAX_LENGTH, COACH_INTRODUCE_MIN_LENGTH } from '@/Coach/constants';

export const isValidIntroduceLength = (text: string) => {
  return text.length >= COACH_INTRODUCE_MIN_LENGTH && text.length <= COACH_INTRODUCE_MAX_LENGTH;
};
