import styled from 'styled-components';

export const Box = styled.div`
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-image: url(/assets/image/desktop.jpeg);

  div {
    position: fixed;
    left: 3rem;
    top: 50%;
    transform: translateY(-50%);
  }

  div p {
    margin-bottom: 1.5rem;

    font-size: 3.5rem;
    font-weight: 700;
    color: ${({ theme }) => theme.colors.black_50};
  }

  button {
    font-size: 2rem;
    padding: 1.2rem 2.5rem;
  }

  @media ${({ theme }) => theme.devices.tablet()} {
    background-image: url(/assets/image/mobile.jpeg);
    div {
      top: unset;
      bottom: 50px;
      left: 50%;
      transform: translateX(-50%);
    }
  }

  @media ${({ theme }) => theme.devices.tabletM()} {
    div p {
      font-size: 2.5rem;
    }

    button {
      font-size: 1.6rem;
    }
  }
`;
