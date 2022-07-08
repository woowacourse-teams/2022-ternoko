import styled, { css } from 'styled-components';

export type ButtonProps = {
  width?: string;
  height?: string;
  orange?: boolean;
  white?: boolean;
  home?: boolean;
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
  font-size: 1.3rem;
  cursor: pointer;

  ${({ orange }) =>
    orange &&
    css`
      background-color: ${({ theme }) => theme.colors.orange};
      color: ${({ theme }) => theme.colors.pink_200};
      border-radius: 5px;
    `}

  ${({ white }) =>
    white &&
    css`
      border: 0.5px solid ${({ theme }) => theme.colors.black};
      background-color: ${({ theme }) => theme.colors.white_50};
      color: ${({ theme }) => theme.colors.black};
    `}

    ${({ home }) =>
    home &&
    css`
      width: 12rem;
      height: 4rem;
      font-size: 1.6rem;
      transition: transform 0.2s ease-in-out;

      :hover {
        transform: scale(1.2);
      }
    `}
`;

export default Button;
