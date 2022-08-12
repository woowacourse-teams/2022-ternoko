import { useState } from 'react';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import Modal from '@/components/@common/Modal';

type CommentModalProps = {
  show: boolean;
  display: boolean;
  handleCloseModal: () => void;
};

const CommentModal = ({ show, display, handleCloseModal }: CommentModalProps) => {
  const [comment, setComment] = useState('');

  const handleChangeTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(e.target.value);
  };

  return (
    <Modal show={show} display={display} handleCloseModal={handleCloseModal}>
      <h2>자유롭게 한마디를 작성해줘잉~~😎</h2>
      <S.Icon src="/assets/icon/close.png" alt="모달 창 닫기 아이콘" onClick={handleCloseModal} />
      <S.ExampleBox>
        <p>예시 1) 고민이 싹~ 해결되었네요!! 감사합니다.</p>
        <p>예시 2) 항상 코치님 덕분에 즐겁습니다. 오늘 하루도 행복하세요.</p>
      </S.ExampleBox>
      <S.TextArea value={comment} onChange={handleChangeTextarea} />
      <S.ButtonBox>
        <Button width="100%">면담 완료하기</Button>
      </S.ButtonBox>
    </Modal>
  );
};

export default CommentModal;
