import styled, { css } from 'styled-components';

export const Box = styled.div`
  width: fit-content;
`;

export const DateBox = styled.div`
  display: flex;
  gap: 3rem;
  padding-bottom: 3rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding-right: 2.5rem;

  > * {
    width: 48%;
  }
`;
