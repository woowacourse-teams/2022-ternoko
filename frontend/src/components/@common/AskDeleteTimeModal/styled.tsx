import styled from 'styled-components';

import theme from '@/styles/theme';

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 4rem;
`;

export const Icon = styled.img`
  width: 2rem;
  height: 2rem;
  object-fit: cover;
  cursor: pointer;
`;

export const ButtonBox = styled.div`
  display: flex;
  justify-content: space-between;

  button {
    padding: 0 2.5rem;

    font-size: 1.8rem;
  }
`;

export const additionalDimmerStyle = `
  z-index: 3; 
  background-color: unset;
`;

export const additionalFrameStyle = `width: 45rem; background-color: ${theme.colors.white_50}; box-shadow: ${theme.shadows.basic}`;
