import styled, { css, keyframes } from 'styled-components';

import { DayType } from '.';

export const Box = styled.div`
  width: max-content;
  height: max-content;
  position: relative;

  box-shadow: ${({ theme }) => theme.shadows.basic};
  border-radius: 20px;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  font-size: 3rem;
  padding: 2rem;
`;

export const YearPicker = styled.div`
  display: flex;
  gap: 1rem;

  p {
    font-size: 2.5rem;
  }
`;

export const YearChange = styled.p`
  display: grid;
  place-items: center;
  width: 3rem;
  height: 3rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.white_100};
  transition: all 0.2s;
  cursor: pointer;

  :hover {
    transform: scale(1.3);
  }
`;

export const MonthPicker = styled.p`
  transition: all 0.2s;
  cursor: pointer;

  :hover {
    transform: scale(1.2);
    font-weight: 600;
  }
`;

export const Body = styled.div`
  padding: 1rem;
  font-size: 1.5rem;
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

type DayProps = {
  type?: DayType;
  today?: boolean;
};

export const Day = styled.div<DayProps>`
  position: relative;
  display: grid;
  place-items: center;
  width: 6rem;
  height: 6rem;
  padding: 0.5rem;
  animation: ${toTop} 1s;
  cursor: pointer;

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
    
  span {
    position: absolute;
  }

  span:nth-child(1),
  span:nth-child(3) {
    width: 2px;
    height: 0;
    background-color: ${({ theme }) => theme.colors.pink_50};
  }

  span:nth-child(1) {
    bottom: 0;
    left: 0;
  }

  span:nth-child(3) {
    top: 0;
    right: 0;
  }

  span:nth-child(2),
  span:nth-child(4) {
    width: 0;
    height: 2px;
    background-color: ${({ theme }) => theme.colors.pink_50};
  }

  span:nth-child(2) {
    top: 0;
    left: 0;
  }

  span:nth-child(4) {
    bottom: 0;
    right: 0;
  }

  :hover span {
    transition: width 0.15s ease-in-out, height 0.15s ease-in-out;
  }

  :hover span:nth-child(1),
  :hover span:nth-child(3) {
    height: 100%;
  }

  :hover span:nth-child(2),
  :hover span:nth-child(4) {
    width: 100%;
  }
  :hover span:nth-child(2) {
    transition-delay: 0.15s;
  }

  :hover span:nth-child(3) {
    transition-delay: 0.3s;
  }

  :hover span:nth-child(4) {
    transition-delay: 0.45s;
  }
`;

type MonthContainerProps = {
  show: boolean;
};

export const MonthContainer = styled.div<MonthContainerProps>`
  position: absolute;
  top: 0;
  display: grid;
  grid-template-columns: repeat(3, auto);
  gap: 1rem;
  width: 100%;
  height: 100%;

  border-radius: 20px;
  background-color: white;
  padding: 2rem;
  font-size: 1.6rem;

  visibility: hidden;
  pointer-events: none;
  transform: scale(0.5);
  transition: transform 0.2s, opacity 0.2s;
  opacity: 0;

  ${({ show }) =>
    show &&
    css`
      visibility: visible;
      pointer-events: visible;
      transform: scale(1);
      opacity: 1;
    `}

  div {
    display: grid;
    place-items: center;

    border-radius: 10px;
    transition: all 0.2s;
    cursor: pointer;

    :hover {
      background-color: ${({ theme }) => theme.colors.white_100};
      font-weight: 600;
      transform: scale(1.2);
    }
  }
`;
