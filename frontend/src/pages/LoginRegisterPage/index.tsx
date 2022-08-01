import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import InputAreaField from '@/components/InputAreaField';
import TextAreaField from '@/components/TextAreaField';

import { DuplicatedNicknameStatusType, CoachType as UserType } from '@/types/domain';

import {
  getCoachInfoAPI,
  getCrewInfoAPI,
  getDuplicatedNicknameStatusAPI,
  patchCoachInfoAPI,
  patchCrewInfoAPI,
} from '@/api';
import { PAGE } from '@/constants';
import { isOverIntroduceMinLength, isOverNicknameMinLength } from '@/validations';

const LoginRegisterPage = () => {
  const navigate = useNavigate();
  const { search } = useLocation();
  const role = new URLSearchParams(search).get('role');

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
      !isOverNicknameMinLength(nickname) ||
      (role === 'coach' && !isOverIntroduceMinLength(introduce))
    )
      return;

    (async () => {
      const response = await getDuplicatedNicknameStatusAPI(nickname);
      const { exists }: DuplicatedNicknameStatusType = response.data;

      if (exists) {
        alert('닉네임이 중복되었습니다.');

        return;
      }

      if (role === 'coach') {
        await patchCoachInfoAPI({ nickname, introduce });
        navigate(PAGE.COACH_HOME);
      } else {
        await patchCrewInfoAPI({ nickname });
        navigate(PAGE.CREW_HOME);
      }
    })();
  };

  useEffect(() => {
    (async () => {
      const response = await (role === 'coach' ? getCoachInfoAPI() : getCrewInfoAPI());
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
            {role === 'coach' && <h4>한 줄 소개는 크루들에게 보여질 정보입니다.</h4>}
          </S.IntroduceBox>

          <S.InputContainer>
            <InputAreaField
              id="nickname"
              label="닉네임*"
              value={nickname}
              isSubmitted={isSubmitted}
              handleChange={handleChangeNickname}
              checkValidation={isOverNicknameMinLength}
            />
            {role === 'coach' && (
              <TextAreaField
                id="introduce"
                label="한 줄 소개*"
                value={introduce}
                isSubmitted={isSubmitted}
                handleChange={handleChangeIntroduce}
                checkValidation={isOverIntroduceMinLength}
              />
            )}
          </S.InputContainer>
        </S.LeftBox>
        <S.RightBox>
          <S.ProfileBox>
            <img src={imageUrl} alt="코치 프로필" />
            <S.Nickname>{nickname || '닉네임'}</S.Nickname>
            {role === 'coach' && (
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
