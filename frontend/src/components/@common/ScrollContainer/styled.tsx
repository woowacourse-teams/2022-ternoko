import styled from 'styled-components';

const ScrollContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 47rem;
  overflow-y: scroll;
  padding-right: 3rem;

  @media ${({ theme }) => theme.devices.tabletM} {
    flex-direction: column;
    width: 160px;
  }
`;

export default ScrollContainer;
