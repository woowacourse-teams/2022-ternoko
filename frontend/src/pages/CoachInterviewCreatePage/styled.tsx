import styled from 'styled-components';

export const Box = styled.div`
  width: fit-content;
`;

export const DateBox = styled.div`
  display: flex;
  gap: 3rem;
  padding-bottom: 3rem;

  @media ${({ theme }) => theme.devices.tabletM()} {
    flex-direction: column;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding-right: 2.5rem;

  > * {
    width: 48%;
  }
`;

export const HeaderBox = styled.div`
  display: flex;
  justify-content: space-between;
  padding-right: 2.5rem;
  margin-bottom: 2rem;

  button {
    transition: all 0.2s;
  }

  button:hover {
    transform: scale(1.15);
  }
`;
