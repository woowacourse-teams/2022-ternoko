import { useEffect, useState } from 'react';

import * as S from './styled';

type AccordionProps = {
  title: string;
  description: string;
  show: boolean;
};

const Accordion = ({ title, description, show }: AccordionProps) => {
  const [open, setOpen] = useState(true);

  const handleToggleHeader = () => {
    setOpen((prev) => !prev);
  };

  useEffect(() => {
    if (!show) {
      setOpen(true);
    }
  }, [show]);

  return (
    <S.Box>
      <S.Header open={open} onClick={handleToggleHeader}>
        <S.Title open={open}>{title}</S.Title>
        <S.Icon open={open} />
      </S.Header>
      <S.Content open={open}>
        <S.Description>{description}</S.Description>
      </S.Content>
    </S.Box>
  );
};

export default Accordion;
