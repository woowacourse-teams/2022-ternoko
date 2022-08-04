import styled, { css, keyframes } from 'styled-components';

import { TitleType, ToastStatus } from '@/context/ToastProvider';

const toastToLeft = keyframes`
  0%,
  60%,
  75%,
  90%,
  to {
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }
  0% {
    transform: translateX(100%);
  }
  60% {
    transform: translateX(-25px);
  }
  75% {
    transform: translateX(10px);
  }
  90% {
    transform: translateX(-5px);
  }
  to {
    transform: none;
  }
`;

const toastToRight = keyframes`
  40% {
    transform: translateX(-20px);
    

  }
  to {
    transform: translateX(100%);
    
  }
`;

type ToastBoxProps = {
  toastStatus: ToastStatus;
};

type ToastProps = ToastBoxProps & {
  title: TitleType;
};

export const ToastFrame = styled.div<ToastBoxProps>`
  width: 36rem;
  min-height: 11.4rem;

  ${({ toastStatus }) =>
    toastStatus === 'DEAD' &&
    css`
      min-height: 0;
      transition: min-height 0.5s;
    `};
`;

type BoxProps = {
  length: number;
  isReduction: boolean;
};

export const Box = styled.div<BoxProps>`
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  display: flex;
  flex-direction: column;
  height: ${({ length }) => (length - 1) * 11.4}rem;
  transition: all 0.2s linear;

  font-size: 14px;

  ${({ isReduction }) =>
    isReduction &&
    css`
      height: unset;
    `};

  ${ToastFrame}:nth-child(1) {
    display: none;
  }
`;

export const DeleteButton = styled.button`
  transition: transform 0.2s ease-in-out;

  border: none;
  border-radius: 100%;
  color: white;
  font-size: 3rem;
  cursor: pointer;

  :hover {
    transform: scale(1.4);
  }
`;

export const ToastBox = styled.div<ToastBoxProps>`
  position: relative;
  width: 36rem;
`;

export const Toast = styled.div<ToastProps>`
  position: absolute;
  bottom: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2rem;

  border-radius: 10px;
  box-shadow: ${({ theme }) => theme.shadows.basic};
  opacity: 0.9;
  transition: 0.3s ease;
  line-height: 1.6;
  color: white;

  background-color: ${({ theme, title }) =>
    title === 'SUCCESS' ? theme.colors.green_100 : theme.colors.red_100};

  ${DeleteButton} {
    background-color: ${({ theme, title }) =>
      title === 'SUCCESS' ? theme.colors.green_100 : theme.colors.red_100};
  }

  ${({ toastStatus }) =>
    toastStatus === 'ALIVE' &&
    css`
      animation: ${toastToLeft} 0.7s;
    `};

  ${({ toastStatus }) =>
    toastStatus === 'DEAD' &&
    css`
      animation: ${toastToRight} 0.7s;
    `};

  :hover {
    opacity: 1;
  }
`;

export const Content = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
`;

export const TitleIcon = styled.img`
  width: 4rem;
  height: 4rem;
`;

export const Title = styled.p`
  font-size: 2rem;
  font-weight: 700;
`;
