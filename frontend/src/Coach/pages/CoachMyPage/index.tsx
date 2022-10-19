import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from '@/Shared/pages/MyPage/styled';

import Button from '@/Shared/components/Button/styled';
import ErrorMessage from '@/Shared/components/ErrorMessage/styled';
import TitleBox from '@/Shared/components/TitleBox';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';
import { useUserActions } from '@/Shared/context/UserProvider';

import { getCoachInfoAPI, patchCoachInfoAPI } from '@/Coach/api';
import { COACH_INTRODUCE_MAX_LENGTH } from '@/Coach/constants';
import { isValidIntroduceLength } from '@/Coach/validation';

import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants/message';
import { PATH } from '@/Shared/constants/path';
import { isValidNicknameLength } from '@/Shared/validations';

const CoachMyPage = () => {
  const { showToast } = useToastActions();

  const { onLoading, offLoading } = useLoadingActions();

  const { initializeUser } = useUserActions();

  const [imageUrl, setImageUrl] = useState('');
  const [nickname, setNickname] = useState('');
  const [introduce, setIntroduce] = useState('');
  const [isEditMode, setIsEditMode] = useState(false);

  const defaultNicknameRef = useRef('');
  const defaultIntroduceRef = useRef('');

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleChangeIntroduce = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setIntroduce(e.target.value);
  };

  const handleClickModifyButton = () => {
    setIsEditMode(true);
  };

  const handleClickCancelButton = () => {
    setNickname(defaultNicknameRef.current);
    setIntroduce(defaultIntroduceRef.current);
    setIsEditMode(false);
  };

  const handleClickConfirmButton = async () => {
    if (!isValidNicknameLength(nickname) || !isValidIntroduceLength(introduce)) return;

    try {
      onLoading();

      await patchCoachInfoAPI({ nickname, introduce, imageUrl });
      offLoading();
      showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_COACH_INFO);

      setIsEditMode(false);
      initializeUser(null);
    } catch (e) {
      offLoading();
      showToast('ERROR', ERROR_MESSAGE.DUPLICATED_NICKNAME);
    }
  };

  useEffect(() => {
    (async () => {
      const response = await getCoachInfoAPI();

      setNickname(response.data.nickname);
      setImageUrl(response.data.imageUrl);
      setIntroduce(response.data.introduce);
      defaultNicknameRef.current = response.data.nickname;
      defaultIntroduceRef.current = response.data.introduce;
    })();
  }, []);

  return (
    <>
      <TitleBox>마이 페이지</TitleBox>

      <S.Box>
        <S.ProfileBox>
          <img src={imageUrl} alt="사용자 프로필" />
          <S.InfoBox>
            <S.Info>
              <label htmlFor="nickname">닉네임</label>
              {isEditMode ? (
                <S.EditModeBox>
                  <S.Input id="nickname" value={nickname} onChange={handleChangeNickname} />

                  {!isValidNicknameLength(nickname) && (
                    <ErrorMessage>{ERROR_MESSAGE.ENTER_IN_RANGE_NICKNAME}</ErrorMessage>
                  )}
                </S.EditModeBox>
              ) : (
                <p>{nickname}</p>
              )}
            </S.Info>

            <S.Info>
              <label htmlFor="introduce">한 줄 소개</label>
              {isEditMode ? (
                <S.EditModeBox>
                  <S.Textarea
                    id="introduce"
                    value={introduce}
                    maxLength={COACH_INTRODUCE_MAX_LENGTH}
                    onChange={handleChangeIntroduce}
                  />
                  <S.DescriptionBox>
                    <p>
                      {!isValidIntroduceLength(introduce) && (
                        <ErrorMessage>{ERROR_MESSAGE.ENTER_IN_RANGE_INTRODUCE_LENGTH}</ErrorMessage>
                      )}
                    </p>
                    <p>
                      {introduce.length}/{COACH_INTRODUCE_MAX_LENGTH}
                    </p>
                  </S.DescriptionBox>
                </S.EditModeBox>
              ) : (
                <p>{introduce}</p>
              )}
            </S.Info>
          </S.InfoBox>
        </S.ProfileBox>

        {isEditMode ? (
          <S.ButtonContainer>
            <Button width="47%" white={true} onClick={handleClickCancelButton}>
              취소하기
            </Button>
            <Button width="47%" onClick={handleClickConfirmButton}>
              확인하기
            </Button>
          </S.ButtonContainer>
        ) : (
          <S.ButtonContainer>
            <Link to={PATH.COACH_HOME}>
              <Button width="100%" white={true}>
                홈으로
              </Button>
            </Link>
            <Button width="47%" onClick={handleClickModifyButton}>
              수정하기
            </Button>
          </S.ButtonContainer>
        )}
      </S.Box>
    </>
  );
};

export default CoachMyPage;
