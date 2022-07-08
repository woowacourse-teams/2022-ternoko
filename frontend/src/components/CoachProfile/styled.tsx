import styled, { css } from 'styled-components';

export const CoachName = styled.p`
  text-align: center;

  font-size: 1.2rem;
  font-weight: bold;
  padding: 0.3rem 0;
  border-radius: 10px;
`;

export const CoachProfileImage = styled.img`
  width: 110px;
  height: 110px;
  border-radius: 25px;
  margin-bottom: 0.5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_100};
`;

type BoxProps = {
  active?: boolean;
};

export const Box = styled.div<BoxProps>`
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
        /* background-color: ${({ theme }) => theme.colors.pink_200}; */

        /* background-color: #f03e3e; */
        backgroud-color: #ffb7d2;
      }
    `}
`;
