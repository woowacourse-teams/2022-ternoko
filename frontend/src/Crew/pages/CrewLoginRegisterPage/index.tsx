import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from '@/Shared/pages/LoginRegisterPage/styled';

import Button from '@/Shared/components/Button/styled';
import InputAreaField from '@/Shared/components/InputAreaField';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';
import { useUserActions } from '@/Shared/context/UserProvider';

import { getCrewInfoAPI, patchCrewInfoAPI } from '@/Crew/api';

import { getDuplicatedNicknameStatusAPI } from '@/Shared/api';
import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants/message';
import { PATH } from '@/Shared/constants/path';
import { isValidNicknameLength } from '@/Shared/validations';

const CrewLoginRegisterPage = () => {
  const navigate = useNavigate();

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();
  const { initializeUser } = useUserActions();

  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!isValidNicknameLength(nickname)) return;

    (async () => {
      try {
        onLoading();

        const response = await getDuplicatedNicknameStatusAPI(nickname);
        const { exists } = response.data;

        if (exists) {
          offLoading();
          showToast('ERROR', ERROR_MESSAGE.DUPLICATED_NICKNAME);

          return;
        }

        await patchCrewInfoAPI({ nickname, imageUrl });
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_CREW_INFO);
        initializeUser(() => navigate(PATH.CREW_HOME));
      } catch (error) {
        offLoading();
      }
    })();
  };

  useEffect(() => {
    (async () => {
      const response = await getCrewInfoAPI();
      const { name, imageUrl } = response.data;

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
          </S.InputContainer>
        </S.LeftBox>
        <S.RightBox>
          <S.ProfileBox>
            <img src={imageUrl} alt="프로필" />
            <S.Nickname>{nickname || '닉네임'}</S.Nickname>
          </S.ProfileBox>
          <Button width="100%" height="3.5rem">
            완료하기
          </Button>
        </S.RightBox>
      </S.Form>
    </S.Box>
  );
};
export default CrewLoginRegisterPage;
