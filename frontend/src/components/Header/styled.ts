import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 9rem;
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
