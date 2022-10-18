import { useState } from 'react';

import { SelectModeType } from '@/Types/domain';

type useTimesProps = {
  selectMode: SelectModeType;
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

    selectMode === 'SINGLE'
      ? setSelectedTimes([time])
      : setSelectedTimes((prev) => [...prev, time]);
  };

  const resetTimes = () => setSelectedTimes([]);

  return { selectedTimes, getHandleClickTime, resetTimes, setSelectedTimes };
};

export default useTimes;
