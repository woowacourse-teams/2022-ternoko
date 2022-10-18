import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 5rem;
  width: 350px;

  margin: 4rem auto 0;

  @media ${({ theme }) => theme.devices.mobileM()} {
    width: 300px;
  }
`;

export const LogoBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  h2 {
    font-size: 2rem;
  }
`;

export const Logo = styled.img`
  width: 110px;
  height: 110px;
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const Info = styled.div`
  display: flex;
  gap: 3rem;
  font-size: 1.5rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;

  a {
    width: 47%;
  }
`;
