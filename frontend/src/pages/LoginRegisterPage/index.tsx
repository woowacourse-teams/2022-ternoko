import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import InputAreaField from '@/components/InputAreaField';
import TextAreaField from '@/components/TextAreaField';

import { useLoadingActions } from '@/context/LoadingProvider';
import { useToastActions } from '@/context/ToastProvider';
import { useUserActions } from '@/context/UserProvider';

import { DuplicatedNicknameStatusType, CoachType as UserType } from '@/types/domain';

import {
  getCoachInfoAPI,
  getCrewInfoAPI,
  getDuplicatedNicknameStatusAPI,
  patchCoachInfoAPI,
  patchCrewInfoAPI,
} from '@/api';
import { COACH_INTRODUCE_MAX_LENGTH, ERROR_MESSAGE, PAGE, SUCCESS_MESSAGE } from '@/constants';
import LocalStorage from '@/localStorage';
import { isValidIntroduceLength, isValidNicknameLength } from '@/validations';

const LoginRegisterPage = () => {
  const navigate = useNavigate();

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();
  const { initializeUser } = useUserActions();

  const { onLoading, offLoading } = useLoadingActions();

  const memberRole = LocalStorage.getMemberRole();

  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [introduce, setIntroduce] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleChangeIntroduce = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduce(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    if (
      !isValidNicknameLength(nickname) ||
      (memberRole === 'COACH' && !isValidIntroduceLength(introduce))
    )
      return;

    (async () => {
      try {
        onLoading();

        const response = await getDuplicatedNicknameStatusAPI(nickname);
        const { exists }: DuplicatedNicknameStatusType = response.data;

        if (exists) {
          showToast('ERROR', ERROR_MESSAGE.DUPLICATED_NICKNAME);

          return;
        }

        if (memberRole === 'COACH') {
          await patchCoachInfoAPI({ nickname, introduce, imageUrl });
          offLoading();
          showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_COACH_INFO);
          initializeUser(() => navigate(PAGE.COACH_HOME));
        } else {
          await patchCrewInfoAPI({ nickname, imageUrl });
          offLoading();
          showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_CREW_INFO);
          initializeUser(() => navigate(PAGE.CREW_HOME));
        }
      } catch (error) {
        offLoading();
      }
    })();
  };

  useEffect(() => {
    (async () => {
      const response = await (memberRole === 'COACH' ? getCoachInfoAPI() : getCrewInfoAPI());
      const { name, imageUrl }: UserType = response.data;

      setName(name);
      setImageUrl(imageUrl);
    })();
  }, []);

  return (
    <S.Box>
      <S.Form onSubmit={handleSubmit}>
        <S.LeftBox>
          <S.IntroduceBox>
            <h3>{name}님, 환영합니다!</h3>
            <h3>기본 정보를 작성해주세요.</h3>
            {memberRole === 'COACH' && <h4>한 줄 소개는 크루들에게 보여질 정보입니다.</h4>}
          </S.IntroduceBox>

          <S.InputContainer>
            <InputAreaField
              id="nickname"
              label="닉네임*"
              value={nickname}
              message={ERROR_MESSAGE.ENTER_IN_RANGE_NICKNAME}
              isSubmitted={isSubmitted}
              handleChange={handleChangeNickname}
              checkValidation={isValidNicknameLength}
            />
            {memberRole === 'COACH' && (
              <TextAreaField
                id="introduce"
                label="한 줄 소개*"
                value={introduce}
                maxLength={COACH_INTRODUCE_MAX_LENGTH}
                message={ERROR_MESSAGE.ENTER_IN_RANGE_INTRODUCE_LENGTH}
                isSubmitted={isSubmitted}
                handleChange={handleChangeIntroduce}
                checkValidation={isValidIntroduceLength}
              />
            )}
          </S.InputContainer>
        </S.LeftBox>
        <S.RightBox>
          <S.ProfileBox>
            <img src={imageUrl} alt="코치 프로필" />
            <S.Nickname>{nickname || '닉네임'}</S.Nickname>
            {memberRole === 'COACH' && (
              <S.Introduce>{introduce || '한 줄 소개를 입력해주세요.'}</S.Introduce>
            )}
          </S.ProfileBox>
          <Button width="100%" height="3.5rem">
            완료하기
          </Button>
        </S.RightBox>
      </S.Form>
    </S.Box>
  );
};
export default LoginRegisterPage;
