import InterviewDetailModal from '../../components/InterviewDetailModal';

import { useState } from 'react';

const TestPage = () => {
  const [modalOn, setModalOn] = useState(false);

  const handleOpenModal = () => {
    console.log('click', modalOn);
    setModalOn(true);
  };

  const handleCloseModal = () => {
    setModalOn(false);
  };
  return (
    <>
      <button onClick={handleOpenModal}>모달 켜기</button>
      <InterviewDetailModal role="coach" modalOn={modalOn} handleCloseModal={handleCloseModal} />
    </>
  );
};

export default TestPage;
