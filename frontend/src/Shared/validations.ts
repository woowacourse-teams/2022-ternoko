import {
  COMMENT_MAX_LENGTH,
  COMMENT_MIN_LENGTH,
  NICKNAME_MAX_LENGTH,
  NICKNAME_MIN_LENGTH,
} from '@/Shared/constants';

export const isValidNicknameLength = (text: string) => {
  return text.length >= NICKNAME_MIN_LENGTH && text.length <= NICKNAME_MAX_LENGTH;
};

export const isValidCommentLength = (text: string) => {
  return text.length >= COMMENT_MIN_LENGTH && text.length <= COMMENT_MAX_LENGTH;
};
