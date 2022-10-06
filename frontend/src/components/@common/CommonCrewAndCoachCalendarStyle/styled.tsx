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
  mark?: boolean;
};

export const CalendarDay = styled(Day)<CalendarDayProps>`
  position: relative;
  display: grid;
  place-items: center;
  width: 6rem;
  height: 6rem;

  @media ${({ theme }) => theme.devices.tabletM()} {
    width: 5.3rem;
    height: 5.3rem;
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
