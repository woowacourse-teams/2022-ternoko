import styled, { css } from 'styled-components';

export type ButtonProps = {
  width?: string;
  height?: string;
  orange?: boolean;
  white?: boolean;
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

  background-color: ${({ theme }) => theme.colors.pink_200};
  color: ${({ theme }) => theme.colors.white_50};
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;

  ${({ orange }) =>
    orange &&
    css`
      background-color: ${({ theme }) => theme.colors.orange};
      color: ${({ theme }) => theme.colors.pink_200};
    `}

  ${({ white }) =>
    white &&
    css`
      border: 0.5px solid ${({ theme }) => theme.colors.black};
      background-color: ${({ theme }) => theme.colors.white_50};
      color: ${({ theme }) => theme.colors.black};
    `}
`;

export default Button;
