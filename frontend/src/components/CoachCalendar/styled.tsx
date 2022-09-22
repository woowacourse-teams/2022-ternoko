import styled, { css } from 'styled-components';

import { Day } from '@/components/@common/CalendarStyle/styled';

import { DayType, InterviewStatus } from '@/types/domain';

export const Box = styled.div`
  width: 100%;
  height: max-content;
  position: relative;

  box-shadow: ${({ theme }) => theme.shadows.basic};
  border-radius: 20px;
`;

type CalendarDayProps = {
  type?: DayType;
  today?: boolean;
};

export const CalendarDay = styled(Day)<CalendarDayProps>`
  text-align: center;
  height: 10rem;
  overflow-y: scroll;

  ${({ type }) =>
    type === 'active' &&
    css`
      border: 1px solid ${({ theme }) => theme.colors.pink_200};
    `}

  ${({ type }) =>
    type === 'disable' &&
    css`
      background-color: ${({ theme }) => theme.colors.gray_100};
      cursor: default;
    `}
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
  status: InterviewStatus;
  padding?: number;
};

export const Schedule = styled.div<ScheduleProps>`
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: ${({ padding }) => padding ?? 0.5}rem;
  margin-top: 0.5rem;
  border-radius: 10px;
  font-weight: 600;
  font-size: 1.3rem;

  background-color: ${({ theme }) => theme.colors.pink_50};

  ${({ theme, status }) =>
    status === 'COMMENT' &&
    css`
      display: flex;
      align-items: center;
      justify-content: space-between;

      background-color: ${theme.colors.white_50};

      @media ${theme.devices.tabletM(50)} {
        flex-direction: column;
        gap: 0.5rem;
      }
    `};
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
