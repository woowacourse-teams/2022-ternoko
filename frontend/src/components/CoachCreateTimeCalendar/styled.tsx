import styled from 'styled-components';

type AllTimeButtonTypes = {
  active: boolean;
};

export const AllTimeButton = styled.button<AllTimeButtonTypes>`
  margin-bottom: 1rem;
  padding: 7px 12px;
  border: none;
  border-radius: 12px;
  font-weight: bold;
  font-size: 1.3rem;
  cursor: pointer;

  background-color: ${({ theme, active }) =>
    active ? theme.colors.gray_150 : theme.colors.white_50};

  transition: background-color 0.2s linear;
`;
