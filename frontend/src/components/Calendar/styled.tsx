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

type AllTimeButtonTypes = {
  active: boolean;
};

export const AllTimeButton = styled.button<AllTimeButtonTypes>`
  margin-bottom: 1rem;
  padding: 7px 12px;
  border: none;
  border-radius: 12px;
  font-weight: bold;
  font-size: 1.3rem;
  cursor: pointer;

  background-color: ${({ theme, active }) =>
    active ? theme.colors.gray_150 : theme.colors.white_50};

  transition: background-color 0.2s linear;
`;
