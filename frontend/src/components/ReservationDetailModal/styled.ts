import styled, { css } from 'styled-components';

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
  padding: 2rem 3rem;

  font-size: 1.5rem;
  background-color: ${({ theme }) => theme.colors.white_100};
`;

export const IconContainer = styled.div`
  position: absolute;
  right: 0;
  top: 0;
  display: flex;
  gap: 0.5rem;
  padding: 0.5rem 0.5rem 0 0;
`;

type IconProps = {
  active?: boolean;
};

export const Icon = styled.img<IconProps>`
  width: 2rem;
  height: 2rem;
  object-fit: cover;

  ${({ active }) =>
    active &&
    css`
      cursor: pointer;
    `};
`;

export const Profile = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-bottom: 3rem;

  img {
    width: 70px;
    height: 70px;
    object-fit: cover;
    border-radius: 100%;
  }
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const Info = styled.div`
  display: flex;
  align-items: flex-start;
`;

export const IconBox = styled.div`
  width: 4.5rem;

  img {
    width: 1.5rem;
    height: 1.5rem;
    object-fit: cover;
  }
`;
