import { useState } from 'react';

import { SelectMode } from '../types/domain';

type useTimesProps = {
  selectMode: SelectMode;
};

const useTimes = ({ selectMode }: useTimesProps) => {
  const [selectedTimes, setSelectedTimes] = useState<string[]>([]);

  const isSelectedTime = (time: string) =>
    selectedTimes.some((selectedTime) => selectedTime === time);

  const getHandleClickTime = (time: string) => () => {
    if (isSelectedTime(time)) {
      setSelectedTimes((prev) => prev.filter((selectedTime) => selectedTime !== time));

      return;
    }

    selectMode === 'single'
      ? setSelectedTimes([time])
      : setSelectedTimes((prev) => [...prev, time]);
  };

  return { selectedTimes, getHandleClickTime };
};

export default useTimes;
