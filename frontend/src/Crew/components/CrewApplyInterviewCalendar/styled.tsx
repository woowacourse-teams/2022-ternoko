import styled from 'styled-components';

export const WeekDay = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  margin-bottom: 1rem;
  font-weight: bold;

  div {
    display: grid;
    place-items: center;
    color: ${({ theme }) => theme.colors.gray_150};
  }
`;

export const Days = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 0.2rem;
`;
