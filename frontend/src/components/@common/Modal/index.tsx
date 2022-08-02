import ReactDOM from 'react-dom';

import * as S from './styled';

type ModalProps = {
  show: boolean;
  display: boolean;
  additionalFrameStyle: string;
  children: React.ReactNode;
  handleCloseModal: () => void;
};

const Modal = ({ show, display, additionalFrameStyle, children, handleCloseModal }: ModalProps) => {
  return ReactDOM.createPortal(
    <S.Dimmer show={show} display={display ? 1 : 0} onClick={handleCloseModal}>
      <S.Frame
        show={show}
        additionalFrameStyle={additionalFrameStyle}
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </S.Frame>
    </S.Dimmer>,
    document.getElementById('modal') as HTMLElement,
  );
};

export default Modal;
