import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 30rem;

  h1 {
    font-family: 'EarlyFontDiary';
    font-size: 3em;
  }

  a {
    display: flex;
    align-items: center;
    gap: 1rem;
  }

  img {
    width: 50px;
  }
`;

export const MenuBox = styled.div`
  display: flex;
  gap: 1rem;
`;

export const MenuItem = styled.p`
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
`;
