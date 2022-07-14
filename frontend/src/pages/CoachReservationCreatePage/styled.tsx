import styled, { css } from 'styled-components';

export const Box = styled.div`
  width: fit-content;
`;

export const DateBox = styled.div`
  display: flex;
  gap: 3rem;
  padding-bottom: 3rem;
`;

export const TimeContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 47rem;
  overflow-y: scroll;
  padding-right: 3rem;
`;

type TimeProps = {
  active: boolean;
};

export const Time = styled.div<TimeProps>`
  padding: 1.5rem 5rem;
  color: ${({ theme }) => theme.colors.pink_200};
  border: 1px solid ${({ theme }) => theme.colors.gray_150};
  color: black;
  font-weight: 600;
  font-size: 1.4rem;
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

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding-right: 2.5rem;

  > * {
    width: 48%;
  }
`;
