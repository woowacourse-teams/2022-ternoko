import React, { useState } from 'react';

import * as S from './styled';

import Button from '../../components/@common/Button/styled';
import TextAreaField from '../../components/TextAreaField';
import InputAreaField from '../../components/InputAreaField';

import { isOverNicknameMinLength, isOverIntroduceMinLength } from '../../validations';

const imageURL = 'https://www.tfmedia.co.kr/data/photos/20210730/art_1627367323247_0d08c3.jpg';

const LoginRegisterPage = () => {
  const [nickname, setNickname] = useState('');
  const [introduce, setIntroduce] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleChangeIntroduce = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduce(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    if (!isOverNicknameMinLength(nickname) || !isOverIntroduceMinLength(introduce)) return;
  };

  return (
    <S.Box>
      <S.Form onSubmit={handleSubmit}>
        <S.LeftBox>
          <S.IntroduceBox>
            <h3>장현석님, 환영합니다!</h3>
            <h3>닉네임과 한 줄 소개를 작성해주세요.</h3>
            <h4>한 줄 소개는 크루들에게 보여질 정보입니다.</h4>
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
            <TextAreaField
              id="introduce"
              label="한 줄 소개*"
              value={introduce}
              isSubmitted={isSubmitted}
              handleChange={handleChangeIntroduce}
              checkValidation={isOverIntroduceMinLength}
            />
          </S.InputContainer>
        </S.LeftBox>
        <S.RightBox>
          <S.ProfileBox>
            <img src={imageURL} alt="코치 프로필" />

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

export default LoginRegisterPage;