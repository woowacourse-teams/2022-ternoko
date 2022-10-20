import * as S from './styled';

import Button from '@/Shared/components/Button/styled';
import Modal from '@/Shared/components/Modal';

import { useLoadingActions } from '@/Shared/context/LoadingProvider';
import { useToastActions } from '@/Shared/context/ToastProvider';

import { deleteCoachInterviewAPI } from '@/Coach/api';

import { CONFIRM_DELETE_MESSAGE, SUCCESS_MESSAGE } from '@/Shared/constants';

type AskDeleteTimeModalProps = {
  show: boolean;
  display: boolean;
  interviewId: number;
  handleCloseModal: () => void;
  afterDeleteInterview: () => void;
};

const AskDeleteTimeModal = ({
  show,
  display,
  interviewId,
  handleCloseModal,
  afterDeleteInterview,
}: AskDeleteTimeModalProps) => {
  const { showToast } = useToastActions();
  const { onLoading, offLoading } = useLoadingActions();

  const handleClickButton = async (onlyInterview: boolean, message: string) => {
    if (confirm(CONFIRM_DELETE_MESSAGE)) {
      try {
        onLoading();
        await deleteCoachInterviewAPI(interviewId, onlyInterview);
        offLoading();
        showToast('SUCCESS', message);
        handleCloseModal();
        afterDeleteInterview();
      } catch (e) {
        offLoading();
      }
    }
  };

  const handleClickYesButton = () =>
    handleClickButton(true, SUCCESS_MESSAGE.COACH_DELETE_INTERVIEW);

  const handleClickNoButton = () =>
    handleClickButton(false, SUCCESS_MESSAGE.COACH_DELETE_INTERVIEW_AND_TIME);

  return (
    <Modal
      show={show}
      display={display}
      additionalDimmerStyle={S.additionalDimmerStyle}
      additionalFrameStyle={S.additionalFrameStyle}
      handleCloseModal={handleCloseModal}
    >
      <S.Header>
        <h2>열어둔 시간은 남겨둘까용?😎</h2>
        <picture>
          <source srcSet="/assets/icon/close.avif" type="image/avif" />
          <S.Icon
            src="/assets/icon/close.png"
            alt="모달 창 닫기 아이콘"
            onClick={handleCloseModal}
          />
        </picture>
      </S.Header>
      <S.ButtonBox>
        <Button white height="4.5rem" onClick={handleClickYesButton}>
          넹
        </Button>
        <Button height="4.5rem" onClick={handleClickNoButton}>
          아니용~ 열어둔 시간도 삭제할게용~
        </Button>
      </S.ButtonBox>
    </Modal>
  );
};

export default AskDeleteTimeModal;
