import styled, { css } from 'styled-components';

export const CoachName = styled.p`
  padding: 0.3rem 0;
  text-align: center;

  font-size: 1.5rem;
  font-weight: bold;
  border-radius: 10px;
`;

export const CoachProfileImage = styled.img`
  width: 110px;
  height: 110px;
  border: 1px solid ${({ theme }) => theme.colors.gray_100};
  border-radius: 20px;
  margin-bottom: 0.5rem;

  @media ${({ theme }) => theme.devices.tablet()} {
    width: 100px;
    height: 100px;
  }

  @media ${({ theme }) => theme.devices.mobileL()} {
    width: 80px;
    height: 80px;
  }

  @media ${({ theme }) => theme.devices.mobileM()} {
    width: 67px;
    height: 67px;
  }
`;

type BoxProps = {
  active?: boolean;
  hasOpenTime: boolean;
};

export const Box = styled.div<BoxProps>`
  position: relative;
  width: fit-content;
  transition: transform 0.2s ease-in-out;
  cursor: pointer;

  :hover {
    transform: translateY(-1.5rem);
  }

  :hover ${CoachName} {
    background-color: ${({ theme }) => theme.colors.pink_50};
  }

  ${({ active }) =>
    active &&
    css`
      ${CoachName} {
        background-color: ${({ theme }) => theme.colors.pink_50};
      }
    `}

  :after {
    content: '';
    position: absolute;
    top: 0;
    right: 0;
    width: 1.2rem;
    height: 1.2rem;
    border-radius: 100%;

    background-color: ${({ theme, hasOpenTime }) =>
      hasOpenTime ? theme.colors.green_100 : theme.colors.pink_200};
  }
`;
