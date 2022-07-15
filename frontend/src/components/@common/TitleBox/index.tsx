import { Link } from 'react-router-dom';

import * as S from './styled';

type TitleBoxProps = {
  to: string;
  title: string;
};

const TitleBox = ({ to, title }: TitleBoxProps) => {
  return (
    <S.TitleBox>
      <h2>
        <Link to={to}>{'<'}</Link> {title}
      </h2>
    </S.TitleBox>
  );
};

export default TitleBox;
