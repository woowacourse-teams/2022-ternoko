import ReactDOM from 'react-dom';
import * as S from './styled';

import Accordion from '../../components/@common/Accordion';

const deleteIconURL =
  'https://icons-for-free.com/download-icon-delete+remove+trash+trash+bin+trash+can+icon-1320073117929397588_512.png';
const closeIconURL = 'https://icons-for-free.com/download-icon-CLOSE-131994911256789607_512.png';
const calendarIconURL = 'https://cdn-icons-png.flaticon.com/512/7088/7088625.png';
const timeIconURL = 'https://cdn-icons-png.flaticon.com/512/88/88291.png';
const profileImageURL =
  'https://user-images.githubusercontent.com/19251499/181178299-d5c7c4c3-249e-47a5-83fe-99c148c3d84b.jpg';
const coachImageURL =
  'https://cdn-icons.flaticon.com/png/512/4623/premium/4623628.png?token=exp=1658972292~hmac=d7731fb9dea54ad0251f9d08247cf4e7';

type ReservationDetailModalProps = {
  role: 'coach' | 'crew';
  show: boolean;
  display: boolean;
  handleCloseModal: () => void;
};

const ReservationDetailModal = ({
  role,
  show,
  display,
  handleCloseModal,
}: ReservationDetailModalProps) => {
  return ReactDOM.createPortal(
    <S.Dimmer open={show} display={display} onClick={handleCloseModal}>
      <S.Frame open={show} onClick={(e) => e.stopPropagation()}>
        <S.IconContainer>
          <S.Icon src={deleteIconURL} alt="삭제 아이콘" active />
          <S.Icon
            src={closeIconURL}
            alt="모달 창 닫기 아이콘"
            active
            agg
            onClick={handleCloseModal}
          />
        </S.IconContainer>
        <S.Profile>
          <img src={profileImageURL} alt="프로필" />
          <p>수달(허수진)</p>
        </S.Profile>
        <S.InfoContainer>
          {role === 'crew' && (
            <S.Info>
              <S.IconBox>
                <S.Icon src={coachImageURL} alt="코치 아이콘" />
              </S.IconBox>
              <p>포코</p>
            </S.Info>
          )}
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
        <S.AccordionContainer>
          <Accordion
            title="이번 면담을 통해 논의하고 싶은 내용"
            description="한 모금에 너를 깨워 심장은 뛰고 온몸으로 퍼져가지 단 한 번에 perfect caffeine, take it away 중독된 것 같은 느낌 lovin"
          />
          <Accordion
            title="최근에 자신이 긍정적으로 보는 시도와 변화"
            description="One shot, twenty-four seven 널 미치게 하는 이 색은 black What you waiting for? look how beautiful 넘치게 넘치게 가득 담아줄게"
          />
          <Accordion
            title="이번 면담을 통해 어떤 변화가 생기기를 원하는지"
            description="더 짙어진 나의 color 더 깊어진 bitter flavor 내 맘은 너만 느낄 수 있어 네 심장을 뛰게 할 나"
          />
        </S.AccordionContainer>
      </S.Frame>
    </S.Dimmer>,
    document.getElementById('modal') as HTMLElement,
  );
};

export default ReservationDetailModal;
