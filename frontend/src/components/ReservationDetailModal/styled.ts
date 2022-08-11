import styled, { css, keyframes } from 'styled-components';

type ModalProps = {
  open?: boolean;
};

export const Dimmer = styled.div<ModalProps>`
  position: fixed;
  left: 0;
  top: -100%;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  background-color: ${({ theme }) => theme.colors.black_50};
  opacity: 0;
  transition: opacity 0.5s top 0.5s;

  ${({ open }) =>
    open &&
    css`
      top: 0;
      transition: top 0.5s;

      opacity: 1;
    `};
`;

export const IconContainer = styled.div`
  position: absolute;
  right: 3rem;
  top: 2rem;
  display: flex;
  gap: 1rem;

  opacity: 0;
`;

type IconProps = {
  active?: boolean;
  agg?: boolean;
};

const rotating = keyframes`
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
`;

export const Icon = styled.img<IconProps>`
  width: 2rem;
  height: 2rem;
  object-fit: cover;

  ${({ active }) =>
    active &&
    css`
      width: 2.5rem;
      height: 2.5rem;
      cursor: pointer;
    `};

  ${({ agg }) =>
    agg &&
    css`
      :hover {
        animation: ${rotating} 0.01s infinite;
      }
    `}
`;

export const Profile = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-bottom: 3rem;

  font-size: 2rem;
  font-weight: 600;
  opacity: 0;

  img {
    width: 8rem;
    height: 8rem;
    object-fit: cover;

    border-radius: 100%;
  }
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  opacity: 0;
`;

export const Info = styled.div`
  display: flex;
  align-items: center;

  font-size: 1.6rem;
`;

export const IconBox = styled.div`
  width: 4.5rem;

  img {
    width: 3rem;
    height: 3rem;
    object-fit: cover;
  }
`;

export const AccordionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 2rem;

  opacity: 0;
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

  ${({ open }) =>
    open &&
    css`
      top: 0;
      transform: scale(1);
      transition: transform 0.2s cubic-bezier(0.18, 0.89, 0.43, 1.19);

      opacity: 1;

      ${IconContainer} {
        transition: opacity 0.3s ease-in-out 0.2s;

        opacity: 1;
      }

      ${Profile} {
        transition: opacity 0.3s ease-in-out 0.25s;

        opacity: 1;
      }

      ${InfoContainer} {
        transition: opacity 0.3s ease-in-out 0.3s;

        opacity: 1;
      }

      ${AccordionContainer} {
        transition: opacity 0.3s ease-in-out 0.35s;

        opacity: 1;
      }
    `}
`;
