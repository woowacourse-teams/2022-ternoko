import { useState } from 'react';
import ReservationDetailModal from '../../components/ReservationDetailModal';

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
      <ReservationDetailModal role="coach" modalOn={modalOn} handleCloseModal={handleCloseModal} />
    </>
  );
};

export default TestPage;
