import { memo } from 'react';

import * as S from './styled';

import {
  monthNames,
  useCalendarActions,
  useCalendarState,
} from '@/Shared/context/CalendarProvider';

import { convertMonthToMonthIndex } from '@/Shared/utils';

export type CalendarProps = {
  children: React.ReactNode;
};

const Calendar = ({ children }: CalendarProps) => {
  const { year, month, showMonthPicker } = useCalendarState();
  const {
    handleClickPrevYear,
    handleClickNextYear,
    handleClickPrevMonth,
    handleClickNextMonth,
    handleClickMonthPicker,
    getHandleClickMonth,
  } = useCalendarActions();

  return (
    <>
      <S.Header>
        <S.YearPicker>
          <S.DateChange onClick={handleClickPrevYear}>{'<'}</S.DateChange>
          <p>{year}</p>
          <S.DateChange onClick={handleClickNextYear}>{'>'}</S.DateChange>
        </S.YearPicker>

        <S.MonthPicker>
          <S.DateChange onClick={handleClickPrevMonth}>{'<'}</S.DateChange>
          <p onClick={handleClickMonthPicker}>{monthNames[convertMonthToMonthIndex(month)]}</p>
          <S.DateChange onClick={handleClickNextMonth}>{'>'}</S.DateChange>
        </S.MonthPicker>
      </S.Header>

      <S.Body>{children}</S.Body>

      <S.MonthContainer show={showMonthPicker}>
        {monthNames.map((monthName, index) => (
          <div key={index} onClick={getHandleClickMonth(index)}>
            {monthName}
          </div>
        ))}
      </S.MonthContainer>
    </>
  );
};

export default memo(Calendar);
