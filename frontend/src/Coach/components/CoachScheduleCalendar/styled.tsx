import styled, { css, keyframes } from 'styled-components';

import { DayType, InterviewStatusType } from '@/common/types/domain';

export const Box = styled.div`
  width: 100%;
  height: max-content;
  position: relative;

  box-shadow: ${({ theme }) => theme.shadows.basic};
  border-radius: 20px;
`;

export const WeekDay = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  margin-bottom: 1rem;
  font-weight: bold;

  div {
    display: grid;
    place-items: center;
    color: ${({ theme }) => theme.colors.gray_150};
  }
`;

export const Days = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 0.2rem;
`;

type CalendarDayProps = {
  type?: DayType;
  today?: boolean;
};

const toTop = keyframes`
  0% {
    transform: translateY(100%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
`;

export const CalendarDay = styled.div<CalendarDayProps>`
  height: 12rem;
  padding: 0.5rem;
  overflow-y: auto;
  text-align: center;
  animation: ${toTop} 1s;

  ${({ type }) =>
    type === 'disable' &&
    css`
      background-color: ${({ theme }) => theme.colors.gray_100};
      cursor: default;
    `}
`;

export const CalendarDayHeader = styled.div`
  width: 100%;
  height: 2.5rem;
  line-height: 2.5rem;
  text-align: center;
  font-size: 1.5rem;
`;

export const Today = styled.p`
  width: 2.5rem;
  height: 2.5rem;
  line-height: 2.5rem;
  text-align: center;
  margin: 0 auto;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.pink_200};
  color: white;
`;

type ScheduleProps = {
  status: InterviewStatusType;
  padding?: number;
};

export const Schedule = styled.div<ScheduleProps>`
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: ${({ padding }) => padding ?? 0.5}rem;
  margin-top: 0.4rem;
  border-radius: 10px;
  font-weight: 600;
  font-size: 1.3rem;
  cursor: pointer;

  background-color: ${({ theme }) => theme.colors.pink_50};

  ${({ theme, status }) =>
    status === 'COMMENT' &&
    css`
      display: flex;
      align-items: center;
      justify-content: space-between;

      background-color: ${theme.colors.white_50};
    `};

  @media ${({ theme }) => theme.devices.tabletM(50)} {
    font-size: 1rem;

    button,
    p {
      font-size: 1rem;
    }
  }
`;

export const ShowMore = styled.div`
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: 0.5rem;
  font-size: 1.3rem;
  text-align: left;
  cursor: pointer;

  color: ${({ theme }) => theme.colors.gray_150};

  :hover {
    background-color: ${({ theme }) => theme.colors.gray_150};
    color: ${({ theme }) => theme.colors.white_50};
  }
`;

export const CrewNickname = styled.p`
  width: 48%;
  display: flex;
  justify-content: center;
  padding: 0.5rem;
  border-radius: 10px;
  font-size: 1.3rem;
  cursor: pointer;

  background-color: ${({ theme }) => theme.colors.pink_50};
`;
