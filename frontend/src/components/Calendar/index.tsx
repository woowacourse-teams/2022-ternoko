import useCalendar from './useCalendar';
import * as S from './styled';

const Calendar = () => {
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
        <S.Days>
          {Array.from({ length: daysLength }, (_, index) => {
            if (isOverFirstDay(index)) {
              const day = getDay(index);

              if (isToday(day)) {
                return <S.Day today>{day}</S.Day>;
              }

              return (
                <S.Day key={index}>
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
          <div onClick={getHandleClickMonth(index)}>{monthName}</div>
        ))}
      </S.MonthContainer>
    </S.Box>
  );
};

export default Calendar;
