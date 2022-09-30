import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  padding-top: 6rem;
  gap: 4rem;
`;

export const EmptyImageWrapper = styled.div`
  width: 28rem;
  height: 28rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.gray_100};
`;

export const EmptyImage = styled.img`
  width: 22rem;
  height: 20rem;
`;

export const EmptyMessage = styled.p`
  font-size: 3rem;
  font-weight: bold;
`;
