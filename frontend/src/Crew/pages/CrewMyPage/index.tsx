import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from '@/Shared/pages/MyPage/styled';

import Button from '@/Shared/components/Button/styled';
import ErrorMessage from '@/Shared/components/ErrorMessage/styled';
import TitleBox from '@/Shared/components/TitleBox';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';
import { useUserActions } from '@/Shared/context/UserProvider';

import { getCrewInfoAPI, patchCrewInfoAPI } from '@/Crew/api';

import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants/message';
import { PATH } from '@/Shared/constants/path';
import { isValidNicknameLength } from '@/Shared/validations';

const CrewMyPage = () => {
  const { showToast } = useToastActions();

  const { onLoading, offLoading } = useLoadingActions();

  const { initializeUser } = useUserActions();

  const [imageUrl, setImageUrl] = useState('');
  const [nickname, setNickname] = useState('');
  const [isEditMode, setIsEditMode] = useState(false);

  const defaultNicknameRef = useRef('');

  const handleChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleClickModifyButton = () => {
    setIsEditMode(true);
  };

  const handleClickCancelButton = () => {
    setNickname(defaultNicknameRef.current);
    setIsEditMode(false);
  };

  const handleClickConfirmButton = async () => {
    if (!isValidNicknameLength(nickname)) return;

    try {
      onLoading();

      await patchCrewInfoAPI({ nickname, imageUrl });
      offLoading();
      showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_CREW_INFO);

      setIsEditMode(false);
      initializeUser(null);
    } catch (e) {
      offLoading();
      showToast('ERROR', ERROR_MESSAGE.DUPLICATED_NICKNAME);
    }
  };

  useEffect(() => {
    (async () => {
      const response = await getCrewInfoAPI();

      setNickname(response.data.nickname);
      setImageUrl(response.data.imageUrl);
      defaultNicknameRef.current = response.data.nickname;
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
            <Link to={PATH.CREW_HOME}>
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

export default CrewMyPage;
