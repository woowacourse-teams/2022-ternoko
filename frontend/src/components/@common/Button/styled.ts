import styled, { css } from 'styled-components';

export type ButtonProps = {
  width?: string;
  gray?: boolean;
};

export const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  width: ${({ width }) => width || ''};
  padding: 7px 12px;
  border: none;
  border-radius: 12px;

  background-color: ${({ theme }) => theme.colors.pink};
  color: white;
  font-weight: bold;
  font-size: 1rem;

  ${({ gray }) =>
    gray &&
    css`
      background-color: ${({ theme }) => theme.colors.orange};
      color: ${({ theme }) => theme.colors.pink};
    `}
`;
