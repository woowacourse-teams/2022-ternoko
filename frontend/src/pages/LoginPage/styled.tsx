import styled, { css, keyframes } from 'styled-components';

export const Box = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;

  @media ${({ theme }) => theme.devices.tablet()} {
    flex-direction: column;
  }
`;

export const LeftBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 45%;
  height: 100%;

  @media ${({ theme }) => theme.devices.tablet()} {
    width: 100%;
  }

  > div {
    h3 {
      margin-bottom: 1.5rem;

      font-size: 4rem;
    }

    h2 {
      margin-bottom: 1.5rem;

      font-size: 5rem;
      color: ${({ theme }) => theme.colors.pink_200};
    }
  }

  @media ${({ theme }) => theme.devices.labtop()} {
    > div {
      h3 {
        font-size: 3rem;
      }

      h2 {
        font-size: 4rem;
        color: ${({ theme }) => theme.colors.pink_200};
      }
    }
  }
`;

export const LoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 2rem;
  width: 100%;
  height: 6rem;

  font-size: 2rem;
  background-color: ${({ theme }) => theme.colors.purple};
  color: ${({ theme }) => theme.colors.white_50};
  cursor: pointer;

  img {
    width: 30px;
  }
`;

export const LogoBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 4rem;

  h1 {
    font-family: EarlyFontDiary;
    font-size: 6rem;
  }

  img {
    width: 100px;
  }
`;
