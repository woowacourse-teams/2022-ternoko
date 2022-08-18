import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import ErrorMessage from '@/components/@common/ErrorMessage/styled';
import TitleBox from '@/components/@common/TitleBox';

import { useLoadingActions } from '@/context/LoadingProvider';
import { useToastActions } from '@/context/ToastProvider';
import { useUserActions } from '@/context/UserProvider';

import { CoachType as UserType } from '@/types/domain';

import { getCoachInfoAPI, getCrewInfoAPI, patchCoachInfoAPI, patchCrewInfoAPI } from '@/api';
import { COACH_INTRODUCE_MAX_LENGTH, ERROR_MESSAGE, PAGE, SUCCESS_MESSAGE } from '@/constants';
import LocalStorage from '@/localStorage';
import { isValidIntroduceLength, isValidNicknameLength } from '@/validations';

const MyPage = () => {
  const memberRole = LocalStorage.getMemberRole();

  const { showToast } = useToastActions();

  const { onLoading, offLoading } = useLoadingActions();

  const { initializeUser } = useUserActions();

  const [imageUrl, setImageUrl] = useState('');
  const [nickname, setNickname] = useState('');
  const [introduce, setIntroduce] = useState('');
  const [isEditMode, setIsEditMode] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);

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
    isSubmitted || setIsSubmitted(true);

    if (
      !isValidNicknameLength(nickname) ||
      (memberRole === 'COACH' && !isValidIntroduceLength(introduce))
    )
      return;

    try {
      onLoading();

      if (memberRole === 'CREW') {
        await patchCrewInfoAPI({ nickname, imageUrl });
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_CREW_INFO);
      } else {
        await patchCoachInfoAPI({ nickname, introduce, imageUrl });
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_COACH_INFO);
      }
    } catch (e) {
      offLoading();
    } finally {
      setIsEditMode(false);
      initializeUser(null);
    }
  };

  useEffect(() => {
    (async () => {
      const response = await (memberRole === 'CREW' ? getCrewInfoAPI() : getCoachInfoAPI());
      const user: UserType = response.data;

      setNickname(user.nickname);
      setImageUrl(user.imageUrl);
      setIntroduce(user.introduce);

      defaultNicknameRef.current = user.nickname;
      defaultIntroduceRef.current = user.introduce;
    })();
  }, []);

  return (
    <>
      <TitleBox title="마이 페이지" />

      <S.Box>
        <S.ProfileBox>
          <img src={imageUrl} alt="사용자 프로필" />
          <S.InfoBox>
            <S.Info>
              <label htmlFor="nickname">닉네임</label>
              {isEditMode ? (
                <S.EditModeBox>
                  <S.Input id="nickname" value={nickname} onChange={handleChangeNickname} />

                  {isSubmitted && !isValidNicknameLength(nickname) && (
                    <ErrorMessage>{ERROR_MESSAGE.ENTER_IN_RANGE_NICKNAME}</ErrorMessage>
                  )}
                </S.EditModeBox>
              ) : (
                <p>{nickname}</p>
              )}
            </S.Info>
            {memberRole === 'COACH' && (
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
                        {isSubmitted && !isValidIntroduceLength(introduce) && (
                          <ErrorMessage>
                            {ERROR_MESSAGE.ENTER_IN_RANGE_INTRODUCE_LENGTH}
                          </ErrorMessage>
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
            )}
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
            <Link to={memberRole === 'COACH' ? PAGE.COACH_HOME : PAGE.CREW_HOME}>
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

export default MyPage;
