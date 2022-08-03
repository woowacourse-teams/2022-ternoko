import styled, { css, keyframes } from 'styled-components';

import { TitleType, ToastStatus } from '@/context/ToastProvider';

const toastToLeft = keyframes`
 from {
    transform: translateX(100%);
  }
  to {
    transform: translateX(0);
  }
`;

const toastToRight = keyframes`
  from {
    transform: translate(0, 0);   
  }
  to {
    transform: translate(100%, -140%);
  }
`;

export const Box = styled.div`
  position: fixed;
  bottom: 2rem;
  right: 2rem;

  display: flex;
  flex-direction: column;
  z-index: 10;

  font-size: 14px;
  transition: all 0.4s ease-in-out;
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

type ToastBoxProps = {
  toastStatus: ToastStatus;
};

type ToastProps = ToastBoxProps & {
  title: TitleType;
};

export const ToastBox = styled.div<ToastBoxProps>`
  position: relative;
  width: 36rem;
  min-height: 12rem;

  ${({ toastStatus }) =>
    toastStatus === 'ALIVE' &&
    css`
      animation: ${toastToLeft} 0.7s;
    `};

  ${({ toastStatus }) =>
    toastStatus === 'DEAD' &&
    css`
      min-height: 0;
      transition: min-height 0.5s;
    `};
`;

export const Toast = styled.div<ToastProps>`
  position: absolute;
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
