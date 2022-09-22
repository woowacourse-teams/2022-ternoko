import styled, { css } from 'styled-components';

type TimeProps = {
  active: boolean;
};

const Time = styled.div<TimeProps>`
  padding: 1.5rem 5rem;
  color: ${({ theme }) => theme.colors.pink_200};
  border: 1px solid ${({ theme }) => theme.colors.gray_150};
  color: black;
  font-weight: 600;
  font-size: 1.4rem;
  text-align: center;
  cursor: pointer;

  :hover {
    background-color: ${({ theme }) => theme.colors.pink_50};
    border: 1px solid ${({ theme }) => theme.colors.pink_200};
  }

  ${({ active }) =>
    active &&
    css`
      background-color: ${({ theme }) => theme.colors.pink_50};
      border: 1px solid ${({ theme }) => theme.colors.pink_200};
    `}
`;

export default Time;
