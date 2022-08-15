import styled from 'styled-components';

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
  z-index: 1; 
  background-color: unset;
`;

export const additionalFrameStyle = 'width: 45rem;';
