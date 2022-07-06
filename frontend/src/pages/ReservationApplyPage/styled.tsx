import styled from 'styled-components';

export const Box = styled.div``;

export const TitleBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;

  h2 {
    position: relative;
    font-size: 3rem;
    cursor: pointer;
  }
`;

export const SubTitleBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 2rem;

  font-weight: bold;

  h3 {
    font-size: 1.3rem;
  }
`;

export const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.pink};
  color: ${({ theme }) => theme.colors.white};
`;
