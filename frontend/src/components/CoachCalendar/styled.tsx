import styled, { css } from 'styled-components';

import { Day } from '../@common/CalendarStyle/styled';

import { DayType } from '../../types/domain';

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

  ${({ today }) =>
    today &&
    css`
      background-color: ${({ theme }) => theme.colors.pink_50};
    `}

  ${({ type }) =>
    type === 'active' &&
    css`
      border: 1px solid ${({ theme }) => theme.colors.pink_200};
    `}

   ${({ type }) =>
    type === 'disable' &&
    css`
      background-color: ${({ theme }) => theme.colors.gray_100};
      color: ${({ theme }) => theme.colors.gray_150};
      pointer-events: none;
      cursor: default;
    `}
`;

export const Schedule = styled.p`
  padding: 0.5rem;

  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.pink_50};
  font-weight: 600;
`;
