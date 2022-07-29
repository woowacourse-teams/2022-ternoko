import styled, { css } from 'styled-components';

type ModalProps = {
  show?: boolean;
  additionalFrameStyle: string;
};

type DimmerProps = Pick<ModalProps, 'show'> & {
  display: boolean;
};

export const Dimmer = styled.div<DimmerProps>`
  position: fixed;
  left: 0;
  top: 0;
  display: ${({ display }) => (display ? 'flex' : 'none')};
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  background-color: ${({ theme }) => theme.colors.black_50};
  opacity: 0;
  transition: opacity 0.5s;

  ${({ show }) =>
    show &&
    css`
      top: 0;

      opacity: 1;
    `};
`;

export const Frame = styled.div<ModalProps>`
  position: relative;
  top: -200%;
  padding: 3rem 3rem;
  transform: scale(0.5);
  transition: opacity 0.5s ease-in-out, top 1s ease-in-out, transform 1s ease-in-out;

  opacity: 0;
  background-color: ${({ theme }) => theme.colors.white_100};
  border-radius: 10px;
  font-size: 1.5rem;

  ${({ show, additionalFrameStyle }) =>
    show &&
    css`
      top: 0;
      transform: scale(1);
      transition: transform 0.2s cubic-bezier(0.18, 0.89, 0.43, 1.19);

      opacity: 1;

      ${additionalFrameStyle}
    `}
`;
