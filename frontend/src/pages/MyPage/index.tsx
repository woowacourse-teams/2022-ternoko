import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import TitleBox from '@/components/@common/TitleBox';

import { useUserActions } from '@/context/UserProvider';

import { CoachType as UserType } from '@/types/domain';

import { getCoachInfoAPI, getCrewInfoAPI, patchCoachInfoAPI, patchCrewInfoAPI } from '@/api';
import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const MyPage = () => {
  const memberRole = LocalStorage.getMemberRole();

  const { initializeUser } = useUserActions();

  const [nickname, setNickname] = useState('');
  const [imageUrl, setImageUrl] = useState('');
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
    //추후 중복 검사 로직
    await (memberRole === 'CREW'
      ? patchCrewInfoAPI({ nickname, imageUrl })
      : patchCoachInfoAPI({ nickname, introduce, imageUrl }));
    initializeUser(null);
    setIsEditMode(false);
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
                <S.Input id="nickname" value={nickname} onChange={handleChangeNickname} />
              ) : (
                <p>{nickname}</p>
              )}
            </S.Info>
            {memberRole === 'COACH' && (
              <S.Info>
                <label htmlFor="introduce">한 줄 소개</label>
                {isEditMode ? (
                  <S.Textarea id="introduce" value={introduce} onChange={handleChangeIntroduce} />
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
