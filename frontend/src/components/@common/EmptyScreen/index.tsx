import * as S from './styled';

import { Valueof } from '@/types/utilities';

import { EMPTY_SCREEN_MESSAGE } from '@/constants';

type EmptyScreenProps = {
  message: Valueof<typeof EMPTY_SCREEN_MESSAGE>;
};

const EmptyScreen = ({ message }: EmptyScreenProps) => {
  return (
    <S.Box>
      <S.InnerBox>
        <picture>
          <source srcSet="/assets/logo/mainLogo.avif" type="image/avif" />
          <S.EmptyImageWrapper>
            <S.EmptyImage src="/assets/logo/mainLogo.png" alt="로고" />
          </S.EmptyImageWrapper>
        </picture>

        <S.EmptyMessage>{message}</S.EmptyMessage>
      </S.InnerBox>
    </S.Box>
  );
};

export default EmptyScreen;
