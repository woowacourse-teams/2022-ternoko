import styled, { css } from 'styled-components';

import { Day } from '@/components/@common/CalendarStyle/styled';

import { DayType } from '@/types/domain';

export const Box = styled.div`
  width: max-content;
  height: max-content;
  position: relative;

  box-shadow: ${({ theme }) => theme.shadows.basic};
  border-radius: 20px;
`;

type CalendarDayProps = {
  type?: DayType;
  today?: boolean;
  mark?: boolean;
};

export const CalendarDay = styled(Day)<CalendarDayProps>`
  position: relative;
  display: grid;
  place-items: center;
  width: 6rem;
  height: 6rem;

  @media ${({ theme }) => theme.devices.tabletM} {
    width: 5.5rem;
    height: 5.5rem;
  }

  ${({ today }) =>
    today &&
    css`
      border-radius: 100%;
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

    ${({ mark }) =>
    mark &&
    css`
      :after {
        content: '';
        position: absolute;
        bottom: 10%;
        width: 0.7rem;
        height: 0.7rem;
        border-radius: 100%;
        background-color: ${({ theme }) => theme.colors.pink_100};
      }
    `}
`;
