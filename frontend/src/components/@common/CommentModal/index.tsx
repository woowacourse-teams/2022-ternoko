import { useEffect, useState } from 'react';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import ErrorMessage from '@/components/@common/ErrorMessage/styled';
import Modal from '@/components/@common/Modal';

import { useLoadingActions } from '@/context/LoadingProvider';
import { useToastActions } from '@/context/ToastProvider';

import { CommentType, MemberRole } from '@/types/domain';

import { getCommentAPI, postCommentAPI, putCommentAPI } from '@/api';
import { COMMENT_MAX_LENGTH, ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/constants';
import { isValidCommentLength } from '@/validations';

type CommentModalProps = {
  show: boolean;
  display: boolean;
  memberRole: MemberRole;
  interviewId: number;
  handleCloseModal: () => void;
};

const CommentModal = ({
  show,
  display,
  memberRole,
  interviewId,
  handleCloseModal,
}: CommentModalProps) => {
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [id, setId] = useState(-1);
  const [comment, setComment] = useState('');

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();

  const handleChangeTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(e.target.value);
  };

  const handleSubmitForm = async (e: React.FormEvent) => {
    e.preventDefault();
    isSubmitted || setIsSubmitted(true);

    try {
      if (!isValidCommentLength(comment)) return;

      if (id === -1) {
        onLoading();
        await postCommentAPI(interviewId, { comment });
        offLoading();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_COMMENT);
      } else {
      }
    } catch (error) {
      offLoading();
    }
  };

  useEffect(() => {
    if (!show) return;

    (async () => {
      const response = await getCommentAPI(Number(interviewId));
      const commentItem = response.data.find(
        ({ role }: CommentType) => role === memberRole,
      ) as CommentType;

      if (commentItem) {
        setId(commentItem.commentId);
        setComment(commentItem.comment);
      }
    })();
  }, [show]);

  return (
    <Modal show={show} display={display} handleCloseModal={handleCloseModal}>
      <h2>자유롭게 한마디를 작성해줘잉~~😎</h2>
      <S.Icon src="/assets/icon/close.png" alt="모달 창 닫기 아이콘" onClick={handleCloseModal} />
      <S.ExampleBox>
        <p>예시 1) 고민이 싹~ 해결되었네요!! 감사합니다.</p>
        <p>예시 2) 항상 코치님 덕분에 즐겁습니다. 오늘 하루도 행복하세요.</p>
      </S.ExampleBox>
      <form onSubmit={handleSubmitForm}>
        <S.TextArea value={comment} onChange={handleChangeTextarea} />
        <S.DescriptionBox>
          <p>
            {isSubmitted && !isValidCommentLength(comment) && (
              <ErrorMessage>{ERROR_MESSAGE.ENTER_IN_RANGE_COMMENT}</ErrorMessage>
            )}
          </p>
          <p>
            {comment.length}/{COMMENT_MAX_LENGTH}
          </p>
        </S.DescriptionBox>
        <S.ButtonBox>
          <Button width="100%" inActive={!isValidCommentLength(comment)}>
            면담 완료하기
          </Button>
        </S.ButtonBox>
      </form>
    </Modal>
  );
};

export default CommentModal;
