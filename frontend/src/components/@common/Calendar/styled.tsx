import styled, { css } from 'styled-components';

export type DayType = 'default' | 'disable' | 'active';

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  font-size: 3rem;
  padding: 2rem;
`;

export const YearPicker = styled.div`
  display: flex;
  gap: 1rem;

  p {
    font-size: 2.5rem;
  }
`;

export const DateChange = styled.p`
  display: grid;
  place-items: center;
  width: 3rem;
  height: 3rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.white_100};
  transition: all 0.2s;
  cursor: pointer;

  :hover {
    transform: scale(1.3);
  }
`;

export const MonthPicker = styled.p`
  display: flex;
  gap: 1rem;

  p {
    font-size: 2.5rem;
    transition: all 0.2s;
    cursor: pointer;
  }

  p:hover {
    transform: scale(1.2);
    font-weight: 600;
  }
`;

export const Body = styled.div`
  padding: 1rem;
  font-size: 1.5rem;
`;

type MonthContainerProps = {
  show: boolean;
};

export const MonthContainer = styled.div<MonthContainerProps>`
  position: absolute;
  top: 0;
  display: grid;
  grid-template-columns: repeat(3, auto);
  gap: 1rem;
  width: 100%;
  height: 100%;

  border-radius: 20px;
  background-color: white;
  padding: 2rem;
  font-size: 1.6rem;

  visibility: hidden;
  pointer-events: none;
  transform: scale(0.5);
  transition: transform 0.2s, opacity 0.2s;
  opacity: 0;

  ${({ show }) =>
    show &&
    css`
      visibility: visible;
      pointer-events: visible;
      transform: scale(1);
      opacity: 1;
    `}

  div {
    display: grid;
    place-items: center;

    border-radius: 10px;
    transition: all 0.2s;
    cursor: pointer;

    :hover {
      background-color: ${({ theme }) => theme.colors.white_100};
      font-weight: 600;
      transform: scale(1.2);
    }
  }
`;
