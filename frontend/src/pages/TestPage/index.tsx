import { useState } from 'react';
import ReservationDetailModal from '../../components/ReservationDetailModal';

const TestPage = () => {
  const [show, setShow] = useState(false);
  const [display, setDisplay] = useState(false);

  const handleOpenModal = () => {
    setDisplay(true);
    setTimeout(() => setShow(true), 0);
  };

  const handleCloseModal = () => {
    setShow(false);
    setTimeout(() => setDisplay(false), 500);
  };

  return (
    <>
      <button onClick={handleOpenModal}>모달 켜기</button>
      <ReservationDetailModal
        role="coach"
        show={show}
        display={display}
        handleCloseModal={handleCloseModal}
      />
    </>
  );
};

export default TestPage;
