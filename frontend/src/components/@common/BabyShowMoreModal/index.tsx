import * as S from './styled';

import { DayNameOfWeekType } from '@/types/domain';

export type BabyModalPositionType = {
  top?: number;
  right?: number;
  bottom?: number;
  left?: number;
};

type BabyShowMoreModalProps = {
  modalPosition: BabyModalPositionType;
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
