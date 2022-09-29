import * as S from './styled';

import { DayOfWeekType, ModalPositionType } from '@/types/domain';

type BabyShowMoreModalProps = {
  modalPosition: ModalPositionType;
  dayOfWeek: DayOfWeekType;
  day: number;
  children: React.ReactNode;
};

const BabyShowMoreModal = ({ modalPosition, dayOfWeek, day, children }: BabyShowMoreModalProps) => {
  return (
    <S.Box {...modalPosition}>
      <S.DayOfWeek>{dayOfWeek}</S.DayOfWeek>
      <S.Day>{day}</S.Day>
      {children}
    </S.Box>
  );
};

export default BabyShowMoreModal;
