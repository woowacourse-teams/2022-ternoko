import React from 'react';
import { Link } from 'react-router-dom';

import * as S from './styled';

type TitleBoxProps = {
  to?: string;
  children: React.ReactNode;
};

const TitleBox = ({ to, children }: TitleBoxProps) => {
  return (
    <S.TitleBox>
      <h2>
        {to && <Link to={to}>{'<'}</Link>}
        {children}
      </h2>
    </S.TitleBox>
  );
};

export default TitleBox;
