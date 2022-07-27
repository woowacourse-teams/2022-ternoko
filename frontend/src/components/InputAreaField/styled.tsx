import styled from 'styled-components';

export const Label = styled.label`
  display: block;
  font-size: 1.4rem;
  font-weight: 600;

  margin-bottom: 0.7rem;
`;

type InputProps = {
  isError: boolean;
};

export const Input = styled.input<InputProps>`
  width: 100%;
  border-radius: 10px;
  padding: 1rem;
  font-size: 1.4rem;
  outline: none;

  border: 1px solid
    ${({ isError, theme }) => (isError ? theme.colors.pink_200 : theme.colors.gray_150)};
`;
