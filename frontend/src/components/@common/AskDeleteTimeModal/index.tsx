import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import Modal from '@/components/@common/Modal';

type AskDeleteTimeModalProps = {
  show: boolean;
  display: boolean;
  handleCloseModal: () => void;
};

const AskDeleteTimeModal = ({ show, display, handleCloseModal }: AskDeleteTimeModalProps) => {
  return (
    <Modal
      show={show}
      display={display}
      additionalFrameStyle={S.additionalFrameStyle}
      handleCloseModal={handleCloseModal}
    >
      <S.Header>
        <h2>열어둔 시간은 남겨둘까용?</h2>
        <S.Icon src="/assets/icon/close.png" alt="모달 창 닫기 아이콘" onClick={handleCloseModal} />
      </S.Header>
      <S.ButtonBox>
        <Button white height="4.5rem">
          넹
        </Button>
        <Button height="4.5rem">아니용~ 열어둔 시간도 삭제할게용~</Button>
      </S.ButtonBox>
    </Modal>
  );
};

export default AskDeleteTimeModal;
