import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 30rem;

  h1 {
    font-family: 'EarlyFontDiary';
    font-size: 3rem;
  }

  a {
    display: flex;
    align-items: center;
    gap: 1rem;

    > img {
      width: 50px;
    }
  }

  @media ${({ theme }) => theme.devices.laptopL} {
    padding: 3rem 20rem 0;
  }

  @media ${({ theme }) => theme.devices.laptop} {
    padding: 3rem 5rem 0;
  }

  @media ${({ theme }) => theme.devices.tablet} {
    padding: 2rem 5rem 0;

    h1 {
      font-size: 2.3rem;
    }
  }

  @media ${({ theme }) => theme.devices.mobileL} {
    padding: 2rem 2rem 0;

    a {
      gap: 0.5rem;
    }

    h1 {
      font-size: 2rem;
    }
  }

  @media ${({ theme }) => theme.devices.mobileM} {
    padding: 2rem 1rem 0;

    a {
      gap: 0.3rem;
    }

    h1 {
      font-size: 1.7rem;
    }
  }
`;

export const MenuBox = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
`;

export const Nickname = styled.p`
  font-size: 1.5rem;
  font-weight: 500;
`;

export const ProfileImage = styled.img`
  width: 45px;
  height: 45px;

  object-fit: cover;
  border-radius: 100%;
  cursor: pointer;
`;
