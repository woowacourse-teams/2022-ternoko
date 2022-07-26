import { useState } from 'react';
import * as S from './styled';

import TitleBox from '../../components/@common/TitleBox';
import Button from '../../components/@common/Button/styled';

const imageURL = 'https://www.tfmedia.co.kr/data/photos/20210730/art_1627367323247_0d08c3.jpg';

const MyPage = () => {
  const [isEditMode, setIsEditMode] = useState(false);

  return (
    <>
      <TitleBox to="/" title="마이 페이지" />
      <S.Box>
        <S.ProfileBox>
          <img src={imageURL} alt="코치 프로필" />
        </S.ProfileBox>
        <S.InfoBox>
          <S.Info>
            <label htmlFor="nickname">닉네임</label>
            <p>포코</p>
          </S.Info>
          <S.Info>
            <label htmlFor="introduce">한 줄 소개</label>
            <p>매운 맛을 느끼고 싶으면 저한테 찾아오세요.</p>
          </S.Info>

          {isEditMode ? (
            <S.ButtonContainer>
              <Button width="47%" white={true}>
                홈으로
              </Button>
              <Button width="47%">수정하기</Button>
            </S.ButtonContainer>
          ) : (
            <S.ButtonContainer>
              <Button width="47%" white={true}>
                취소하기
              </Button>
              <Button width="47%">확인하기</Button>
            </S.ButtonContainer>
          )}
        </S.InfoBox>
      </S.Box>
    </>
  );
};

export default MyPage;
