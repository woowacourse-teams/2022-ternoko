import styled, { css, keyframes } from 'styled-components';

import { DayType } from '@/Shared/types/domain';

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
  position: relative;
  display: grid;
  place-items: center;
  padding: 0.5rem;
  width: 6rem;
  height: 6rem;
  animation: ${toTop} 1s;
  cursor: pointer;

  span {
    position: absolute;
  }

  span:nth-of-type(1),
  span:nth-of-type(3) {
    width: 2px;
    height: 0;
    background-color: ${({ theme }) => theme.colors.pink_50};
  }

  span:nth-of-type(1) {
    bottom: 0;
    left: 0;
  }

  span:nth-of-type(3) {
    top: 0;
    right: 0;
  }

  span:nth-of-type(2),
  span:nth-of-type(4) {
    width: 0;
    height: 2px;
    background-color: ${({ theme }) => theme.colors.pink_50};
  }

  span:nth-of-type(2) {
    top: 0;
    left: 0;
  }

  span:nth-of-type(4) {
    bottom: 0;
    right: 0;
  }

  :hover span {
    transition: width 0.15s ease-in-out, height 0.15s ease-in-out;
  }

  :hover span:nth-of-type(1),
  :hover span:nth-of-type(3) {
    height: 100%;
  }

  :hover span:nth-of-type(2),
  :hover span:nth-of-type(4) {
    width: 100%;
  }
  :hover span:nth-of-type(2) {
    transition-delay: 0.15s;
  }

  :hover span:nth-of-type(3) {
    transition-delay: 0.3s;
  }

  :hover span:nth-of-type(4) {
    transition-delay: 0.45s;
  }

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
