import styled from 'styled-components';

export type ButtonProps = {
  width: string;
  height: string;
};

export const Button = styled.button<ButtonProps>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  border: none;
  border-radius: 12px;

  background-color: ${({ theme }) => theme.colors.pink};
  color: white;
  font-weight: bold;
`;
