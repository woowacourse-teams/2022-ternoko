import ReactDOM from 'react-dom';

import * as S from './styled';

const deleteIconURL =
  'https://icons-for-free.com/download-icon-delete+remove+trash+trash+bin+trash+can+icon-1320073117929397588_512.png';
const closeIconURL = 'https://icons-for-free.com/download-icon-CLOSE-131994911256789607_512.png';
const calendarIconURL = 'https://cdn-icons-png.flaticon.com/512/7088/7088625.png';
const timeIconURL = 'https://cdn-icons-png.flaticon.com/512/88/88291.png';
const profileImageURL =
  'https://www.tfmedia.co.kr/data/photos/20210730/art_1627367323247_0d08c3.jpg';

const ReservationDetailModal = () => {
  return ReactDOM.createPortal(
    <S.Dimmer>
      <S.Frame>
        <S.IconContainer>
          <S.Icon src={deleteIconURL} alt="삭제 아이콘" active />
          <S.Icon src={closeIconURL} alt="모달 창 닫기 아이콘" active />
        </S.IconContainer>
        <S.Profile>
          <img src={profileImageURL} alt="프로필" />
          <p>바니(사현빈)</p>
        </S.Profile>
        <S.InfoContainer>
          <S.Info>
            <S.IconBox>
              <S.Icon src={calendarIconURL} alt="달력 아이콘" />
            </S.IconBox>
            <p>2022년 07월 26일</p>
          </S.Info>
          <S.Info>
            <S.IconBox>
              <S.Icon src={timeIconURL} alt="시간 아이콘" />
            </S.IconBox>
            <p>13:00 ~ 13:30</p>
          </S.Info>
        </S.InfoContainer>
      </S.Frame>
    </S.Dimmer>,
    document.getElementById('modal') as HTMLElement,
  );
};

export default ReservationDetailModal;
