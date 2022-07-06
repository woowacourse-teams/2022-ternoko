import styled from 'styled-components';

export const TitleBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;

  h2 {
    font-size: 3rem;
    cursor: pointer;
  }
`;

export const TabMenuBox = styled.div`
  display: flex;
  gap: 2rem;

  h3 {
    position: relative;
    font-size: 2rem;
  }

  h3:after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -3px;
    width: 100%;
    height: 3px;

    background-color: ${({ theme }) => theme.colors.pink};
  }
`;
