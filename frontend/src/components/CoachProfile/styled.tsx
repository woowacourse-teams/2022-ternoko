import styled, { css } from 'styled-components';

export const CoachProfileImage = styled.img`
  width: 110px;
  height: 110px;
  border-radius: 25px;
  margin-bottom: 0.5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_100};
`;

export const CoachName = styled.p`
  text-align: center;

  font-size: 1.2rem;
  font-weight: bold;
  padding: 0.3rem 0;
  border-radius: 10px;
`;

type BoxProps = {
  active?: boolean;
};

export const Box = styled.div<BoxProps>`
  width: fit-content;
  cursor: pointer;

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
`;
