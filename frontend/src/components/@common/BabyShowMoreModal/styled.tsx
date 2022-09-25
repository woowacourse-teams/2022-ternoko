import styled, { css } from 'styled-components';

import { ModalPositionType } from '@/types/domain';

export const Box = styled.div<ModalPositionType>`
  position: absolute;
  ${({ top }) =>
    top &&
    css`
      top: ${top}px;
    `};
  ${({ right }) =>
    right &&
    css`
      right: ${right}px;
    `};
  ${({ bottom }) =>
    bottom &&
    css`
      bottom: ${bottom}px;
    `};
  ${({ left }) =>
    left &&
    css`
      left: ${left}px;
    `};
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  overflow-y: auto;
  width: 20rem;
  max-height: 50%;
  padding: 1.5rem;
  border-radius: 8px;
  z-index: 1;

  box-shadow: ${({ theme }) => theme.colors.allRound};
  background: ${({ theme }) => theme.colors.white_50};
`;

export const DayOfWeek = styled.p`
  width: 100%;
  text-align: center;
  font-size: 1.4rem;
  margin-bottom: 0.5rem;
`;

export const Day = styled.p`
  width: 100%;
  text-align: center;
  font-size: 1.2rem;
  margin-bottom: 1rem;
`;
