import styled, { css, keyframes } from 'styled-components';

export const Dimmer = styled.div`
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  background-color: ${({ theme }) => theme.colors.black_50};
`;

export const Frame = styled.div`
  position: relative;
  z-index: 1;
  padding: 3rem 3rem;

  font-size: 1.5rem;
  background-color: ${({ theme }) => theme.colors.white_100};
  border-radius: 10px;
`;

export const IconContainer = styled.div`
  position: absolute;
  right: 3rem;
  top: 2rem;
  display: flex;
  gap: 1rem;
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
`;
