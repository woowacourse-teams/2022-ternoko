import * as S from './styled';

import { EMPTY_SCREEN_MESSAGE } from '@/Shared/constants/message';

import { ValueofType } from '@/Types/utilities';

type EmptyScreenProps = {
  message: ValueofType<typeof EMPTY_SCREEN_MESSAGE>;
};

const EmptyScreen = ({ message }: EmptyScreenProps) => {
  return (
    <S.Box>
      <picture>
        <source srcSet="/assets/logo/mainLogo.avif" type="image/avif" />
        <S.EmptyImageWrapper>
          <S.EmptyImage src="/assets/logo/mainLogo.png" alt="로고" />
        </S.EmptyImageWrapper>
      </picture>
      <S.EmptyMessage>{message}</S.EmptyMessage>
    </S.Box>
  );
};

export default EmptyScreen;
