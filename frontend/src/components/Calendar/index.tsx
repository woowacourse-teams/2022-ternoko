import useCalendar from './useCalendar';
import * as S from './styled';

type CalendarProps = {
  currentDay: number;
  handleClickDay: (date: number) => () => void;
};

const Calendar = ({ currentDay, handleClickDay }: CalendarProps) => {
  const {
    daysLength,
    monthNames,
    month,
    year,
    showMonthPicker,
    getHandleClickMonth,
    handleClickPrevYear,
    handleClickNextYear,
    handleClickMonthPicker,
    getDay,
    isToday,
    isOverFirstDay,
  } = useCalendar();
  console.log('hi calendar');
  return (
    <S.Box>
      <S.Header>
        <S.MonthPicker onClick={handleClickMonthPicker}>{monthNames[month]}</S.MonthPicker>
        <S.YearPicker>
          <S.YearChange onClick={handleClickPrevYear}>{'<'}</S.YearChange>
          <p>{year}</p>
          <S.YearChange onClick={handleClickNextYear}>{'>'}</S.YearChange>
        </S.YearPicker>
      </S.Header>
      <S.Body>
        <S.WeekDay>
          <div>Sun</div>
          <div>Mon</div>
          <div>Tue</div>
          <div>Wed</div>
          <div>Thu</div>
          <div>Fri</div>
          <div>Sat</div>
        </S.WeekDay>
        <S.Days key={Date.now()}>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);

              if (isToday(day)) {
                return (
                  <S.Day
                    key={index}
                    today
                    onClick={handleClickDay(day)}
                    active={currentDay === day}
                  >
                    {day}
                  </S.Day>
                );
              }

              return (
                <S.Day key={index} onClick={handleClickDay(day)} active={currentDay === day}>
                  {day}
                  <span />
                  <span />
                  <span />
                  <span />
                </S.Day>
              );
            }

            return <S.Day key={index} />;
          })}
        </S.Days>
      </S.Body>

      <S.MonthContainer show={showMonthPicker}>
        {monthNames.map((monthName, index) => (
          <div key={index} onClick={getHandleClickMonth(index)}>
            {monthName}
          </div>
        ))}
      </S.MonthContainer>
    </S.Box>
  );
};

export default Calendar;
