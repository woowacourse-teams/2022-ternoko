import { useState } from 'react';

const useModal = () => {
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

  return { show, handleOpenModal, display, handleCloseModal };
};

export default useModal;
