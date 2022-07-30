import { useEffect, useRef, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import * as S from './styled';

import TitleBox from '../../components/@common/TitleBox';
import Button from '../../components/@common/Button/styled';

import { PAGE } from '../../constants';

const MyPage = () => {
  const { search } = useLocation();
  const role = new URLSearchParams(search).get('role');

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

  useEffect(() => {
    const nickname = '포코';
    const introduce = '매운 맛을 느끼고 싶으면 저한테 찾아오시오.';

    setNickname(nickname);
    setIntroduce(introduce);

    defaultNicknameRef.current = nickname;
    defaultIntroduceRef.current = introduce;
  }, []);

  return (
    <>
      <TitleBox title="마이 페이지" />

      <S.Box>
        <S.ProfileBox>
          <img src="/assets/image/sudal.png" alt="코치 프로필" />
          <S.InfoBox>
            <S.Info>
              <label htmlFor="nickname">닉네임</label>
              {isEditMode ? (
                <S.Input id="nickname" value={nickname} onChange={handleChangeNickname} />
              ) : (
                <p>{nickname}</p>
              )}
            </S.Info>
            {role === 'coach' && (
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
            <Button width="47%">확인하기</Button>
          </S.ButtonContainer>
        ) : (
          <S.ButtonContainer>
            <Link to={role === 'coach' ? PAGE.COACH_HOME : PAGE.CREW_HOME}>
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
