import styled, { css } from 'styled-components';

export type ButtonProps = {
  width?: string;
  height?: string;
  orange?: boolean;
};

const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  width: ${({ width }) => width || ''};
  height: ${({ height }) => height ?? 'auto'};
  padding: 7px 12px;
  border: none;
  border-radius: 12px;

  background-color: ${({ theme }) => theme.colors.pink};
  color: ${({ theme }) => theme.colors.white};
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;

  ${({ orange }) =>
    orange &&
    css`
      background-color: ${({ theme }) => theme.colors.orange};
      color: ${({ theme }) => theme.colors.pink};
    `}
`;

export default Button;
