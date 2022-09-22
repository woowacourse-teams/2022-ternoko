import React, { useEffect, useState } from 'react';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';
import Modal from '@/components/@common/Modal';

import TextAreaField from '@/components/TextAreaField';

import { useLoadingActions } from '@/context/LoadingProvider';
import { useToastActions } from '@/context/ToastProvider';

import { CommentType, MemberRole } from '@/types/domain';
import { InterviewStatus } from '@/types/domain';

import { getCommentAPI, postCommentAPI, putCommentAPI } from '@/api';
import { COMMENT_MAX_LENGTH, ERROR_MESSAGE, SUCCESS_MESSAGE } from '@/constants';
import { isValidCommentLength } from '@/validations';

type CommentModalProps = {
  show: boolean;
  display: boolean;
  memberRole: MemberRole;
  interviewId: number;
  interviewStatus: InterviewStatus;
  afterPostAndPutComment: () => void;
  handleCloseModal: () => void;
};

const CommentModal = ({
  show,
  display,
  memberRole,
  interviewId,
  interviewStatus,
  afterPostAndPutComment,
  handleCloseModal,
}: CommentModalProps) => {
  const [commentId, setCommentId] = useState(-1);
  const [coachComment, setCoachComment] = useState('');
  const [crewComment, setCrewComment] = useState('');

  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();

  const isCoachCompleted =
    memberRole === 'COACH' && ['COACH_COMPLETED', 'COMPLETED'].includes(interviewStatus);
  const isCrewCompleted =
    memberRole === 'CREW' && ['CREW_COMPLETED', 'COMPLETED'].includes(interviewStatus);

  const handleChangeCoachTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCoachComment(e.target.value);
  };

  const handleChangeCrewTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCrewComment(e.target.value);
  };

  const handleClickButton = async () => {
    try {
      const comment = memberRole === 'CREW' ? crewComment : coachComment;

      if (!isValidCommentLength(comment)) return;

      if (commentId === -1) {
        onLoading();
        await postCommentAPI(interviewId, { comment });
        offLoading();
        afterPostAndPutComment();
        showToast('SUCCESS', SUCCESS_MESSAGE.CREATE_COMMENT);
      } else {
        onLoading();
        await putCommentAPI(interviewId, commentId, { comment });
        offLoading();
        afterPostAndPutComment();
        showToast('SUCCESS', SUCCESS_MESSAGE.UPDATE_COMMENT);
      }
    } catch (error) {
      offLoading();
    }
  };

  useEffect(() => {
    if (!show) {
      memberRole === 'CREW' ? setCrewComment('') : setCoachComment('');
      return;
    }

    (async () => {
      const response = await getCommentAPI(Number(interviewId));
      const coachCommentItem = response.data.comments.find(
        ({ role }: CommentType) => role === 'COACH',
      ) as CommentType;
      const crewCommentItem = response.data.comments.find(
        ({ role }: CommentType) => role === 'CREW',
      ) as CommentType;

      if (coachCommentItem) {
        setCoachComment(coachCommentItem.comment);
        memberRole === 'COACH' && setCommentId(coachCommentItem.commentId);
      }

      if (crewCommentItem) {
        setCrewComment(crewCommentItem.comment);
        memberRole === 'CREW' && setCommentId(crewCommentItem.commentId);
      }
    })();
  }, [show]);

  return (
    <Modal
      show={show}
      display={display}
      handleCloseModal={handleCloseModal}
      additionalFrameStyle={S.additionalFrameStyle}
    >
      <h2>자유롭게 한마디를 작성해줘잉~~😎</h2>
      <picture>
        <source srcSet="/assets/icon/close.avif" type="image/avif" />
        <S.Icon src="/assets/icon/close.png" alt="모달 창 닫기 아이콘" onClick={handleCloseModal} />
      </picture>

      <S.ExampleBox>
        <p>예시 1) 고민이 싹~ 해결되었네요!! 감사합니다.</p>
        <p>예시 2) 항상 덕분에 즐겁습니다. 오늘 하루도 행복하세요.</p>
      </S.ExampleBox>
      <S.Form>
        {(memberRole === 'COACH' || isCrewCompleted) && (
          <div>
            <TextAreaField
              id="coach-comment"
              label="코치의 한마디"
              value={coachComment}
              maxLength={COMMENT_MAX_LENGTH}
              message={isCrewCompleted ? '' : ERROR_MESSAGE.ENTER_IN_RANGE_COMMENT}
              disabled={isCrewCompleted}
              handleChange={handleChangeCoachTextarea}
              checkValidation={isCrewCompleted ? () => true : isValidCommentLength}
            />
            {!isCrewCompleted && (
              <S.ButtonBox>
                <Button
                  width="100%"
                  inActive={!isValidCommentLength(coachComment)}
                  onClick={handleClickButton}
                >
                  코멘트 {commentId === -1 ? '완료' : '수정'}하기
                </Button>
              </S.ButtonBox>
            )}
          </div>
        )}
        {(memberRole === 'CREW' || isCoachCompleted) && (
          <div>
            <TextAreaField
              id="coach-comment"
              label="크루의 한마디"
              value={crewComment}
              maxLength={COMMENT_MAX_LENGTH}
              message={isCoachCompleted ? '' : ERROR_MESSAGE.ENTER_IN_RANGE_COMMENT}
              disabled={isCoachCompleted}
              handleChange={handleChangeCrewTextarea}
              checkValidation={isCoachCompleted ? () => true : isValidCommentLength}
            />
            {!isCoachCompleted && (
              <S.ButtonBox>
                <Button
                  width="100%"
                  inActive={!isValidCommentLength(crewComment)}
                  onClick={handleClickButton}
                >
                  코멘트 {commentId === -1 ? '완료' : '수정'}하기
                </Button>
              </S.ButtonBox>
            )}
          </div>
        )}
      </S.Form>
    </Modal>
  );
};

export default CommentModal;
