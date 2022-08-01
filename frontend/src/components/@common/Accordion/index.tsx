import { useState } from 'react';

import * as S from './styled';

type AccordionProps = {
  title: string;
  description: string;
};

const Accordion = ({ title, description }: AccordionProps) => {
  const [open, setOpen] = useState(true);

  const handleToggleHeader = () => {
    setOpen((prev) => !prev);
  };

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
