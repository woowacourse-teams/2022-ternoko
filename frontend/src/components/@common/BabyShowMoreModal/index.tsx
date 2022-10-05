import * as S from './styled';

import { DayNameOfWeekType, ModalPositionType } from '@/types/domain';

type BabyShowMoreModalProps = {
  modalPosition: ModalPositionType;
  dayNameOfWeek: DayNameOfWeekType;
  day: number;
  children: React.ReactNode;
};

const BabyShowMoreModal = ({
  modalPosition,
  dayNameOfWeek,
  day,
  children,
}: BabyShowMoreModalProps) => {
  return (
    <S.Box {...modalPosition}>
      <S.DayOfWeek>{dayNameOfWeek}</S.DayOfWeek>
      <S.Day>{day}</S.Day>
      {children}
    </S.Box>
  );
};

export default BabyShowMoreModal;
