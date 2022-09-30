import styled from 'styled-components';

export const Box = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  padding-top: 6rem;
`;

export const InnerBox = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 3rem;
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
  height: 22rem;
`;

export const EmptyMessage = styled.p`
  margin-top: 4rem;
  font-size: 4rem;
`;
