import styled, { css } from 'styled-components';

export type ButtonProps = {
  width?: string;
  height?: string;
  inActive?: boolean;
  white?: boolean;
  orange?: boolean;
  home?: boolean;
};

const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  width: ${({ width }) => width ?? ''};
  height: ${({ height }) => height ?? 'auto'};
  padding: 7px 12px;
  border: none;
  border-radius: 12px;

  background-color: ${({ theme }) => theme.colors.pink_200};
  color: ${({ theme }) => theme.colors.white_50};
  font-weight: bold;
  font-size: 1.3rem;
  cursor: pointer;

  ${({ inActive }) =>
    inActive &&
    css`
      opacity: 0.4;
      pointer-events: none;
    `}

  ${({ white }) =>
    white &&
    css`
      background-color: ${({ theme }) => theme.colors.white_50};
      color: ${({ theme }) => theme.colors.black_200};
      border: 0.5px solid ${({ theme }) => theme.colors.black_200};
    `}

  ${({ orange }) =>
    orange &&
    css`
      background-color: ${({ theme }) => theme.colors.orange};
      color: ${({ theme }) => theme.colors.pink_200};
      border-radius: 5px;
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
