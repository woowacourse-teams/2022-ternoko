import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from '@/Shared/pages/LoginRegisterPage/styled';

import Button from '@/Shared/components/Button/styled';
import InputAreaField from '@/Shared/components/InputAreaField';
import TextAreaField from '@/Shared/components/TextAreaField';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';
import { useUserActions } from '@/Shared/context/UserProvider';

import { getCoachInfoAPI, patchCoachInfoAPI } from '@/Coach/api';
import { COACH_INTRODUCE_MAX_LENGTH } from '@/Coach/constants';
import { isValidIntroduceLength } from '@/Coach/validation';

import { getDuplicatedNicknameStatusAPI } from '@/Shared/api';
import { ERROR_MESSAGE, PAGE, SUCCESS_MESSAGE } from '@/Shared/constants';
import { isValidNicknameLength } from '@/Shared/validations';

import { CrewType as UserType } from '@/Types/domain';
import { DuplicatedNicknameStatusType } from '@/Types/domain';

const CoachLoginRegisterPage = () => {
  const navigate = useNavigate();

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();
  const { initializeUser } = useUserActions();

  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [introduce, setIntroduce] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleChangeIntroduce = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduce(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!isValidNicknameLength(nickname) || !isValidIntroduceLength(introduce)) return;

    (async () => {
      try {
        onLoading();

        const response = await getDuplicatedNicknameStatusAPI(nickname);
        const { exists }: DuplicatedNicknameStatusType = response.data;

        if (exists) {
          offLoading();
          showToast('ERROR', ERROR_MESSAGE.DUPLICATED_NICKNAME);

          return;
        }

        await patchCoachInfoAPI({ nickname, introduce, imageUrl });
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_COACH_INFO);
        initializeUser(() => navigate(PAGE.COACH_HOME));
      } catch (error) {
        offLoading();
      }
    })();
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachInfoAPI();
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
            <h4>한 줄 소개는 크루들에게 보여질 정보입니다.</h4>
          </S.IntroduceBox>
          <S.InputContainer>
            <InputAreaField
              id="nickname"
              label="닉네임*"
              value={nickname}
              message={ERROR_MESSAGE.ENTER_IN_RANGE_NICKNAME}
              handleChange={handleChangeNickname}
              checkValidation={isValidNicknameLength}
            />
            <TextAreaField
              id="introduce"
              label="한 줄 소개*"
              value={introduce}
              maxLength={COACH_INTRODUCE_MAX_LENGTH}
              message={ERROR_MESSAGE.ENTER_IN_RANGE_INTRODUCE_LENGTH}
              handleChange={handleChangeIntroduce}
              checkValidation={isValidIntroduceLength}
            />
          </S.InputContainer>
        </S.LeftBox>
        <S.RightBox>
          <S.ProfileBox>
            <img src={imageUrl} alt="프로필" />
            <S.Nickname>{nickname || '닉네임'}</S.Nickname>
            <S.Introduce>{introduce || '한 줄 소개를 입력해주세요.'}</S.Introduce>
          </S.ProfileBox>
          <Button width="100%" height="3.5rem">
            완료하기
          </Button>
        </S.RightBox>
      </S.Form>
    </S.Box>
  );
};
export default CoachLoginRegisterPage;
