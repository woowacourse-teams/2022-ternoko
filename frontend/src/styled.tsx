import { Link } from 'react-router-dom';

import styled from 'styled-components';

export const FloatingButton = styled(Link)`
  position: fixed;
  right: 30px;
  bottom: 30px;
  z-index: 10000;
  border-radius: 100%;

  cursor: pointer;
  padding: 2rem;
  font-size: 2rem;
  color: white;
  background: ${({ theme }) => theme.colors.purple};
`;
