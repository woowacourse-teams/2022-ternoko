import {
  CREW_APPLY_FORM_MIN_LENGTH,
  INTRODUCE_MIN_LENGTH,
  NICKNAME_MAX_LENGTH,
  NICKNAME_MIN_LENGTH,
} from '@/constants';
import {
  isOverApplyFormMinLength,
  isOverIntroduceMinLength,
  isValidNicknameLength,
} from '@/validations';

describe('유효성 검사', () => {
  test(`한 줄 소개의 길이는 ${INTRODUCE_MIN_LENGTH}를 넘어야 한다. `, () => {
    const introduce = '안녕하세요. 저는 따뜻한 마음의 소유자입니다.';

    expect(isOverIntroduceMinLength(introduce)).toBe(true);
  });

  test(`닉네임의 길이가 ${NICKNAME_MIN_LENGTH}이면, false를 반환한다.`, () => {
    const nickname = '';

    expect(isValidNicknameLength(nickname)).toBe(false);
  });

  test(`닉네임의 길이는 ${NICKNAME_MAX_LENGTH}를 초과하면, false를 반환한다.`, () => {
    const nickname = '아놀드록바바니';

    expect(isValidNicknameLength(nickname)).toBe(false);
  });

  test(`면담 신청 입력 폼의 길이는 ${CREW_APPLY_FORM_MIN_LENGTH} 이상이어야 `, () => {
    const input = '좋은 팀 문화가 무엇인지 궁금합니다.';

    expect(isOverApplyFormMinLength(input)).toBe(true);
  });
});
