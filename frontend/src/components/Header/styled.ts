import styled, { css } from 'styled-components';

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

    img {
      width: 7rem;
      aspect-ratio: 1.2;
    }
  }

  @media ${({ theme }) => theme.devices.laptopL()} {
    padding: 0 25rem;
  }

  @media ${({ theme }) => theme.devices.laptop(50)} {
    padding: 0 5rem;
  }

  @media ${({ theme }) => theme.devices.tabletM()} {
    h1 {
      font-size: 2.3rem;
    }
  }

  @media ${({ theme }) => theme.devices.mobileL(30)} {
    padding: 0 2rem;

    a {
      gap: 0.5rem;
    }

    h1 {
      font-size: 2.3rem;
    }
  }

  @media ${({ theme }) => theme.devices.mobileM()} {
    padding: 0 1rem;

    a {
      gap: 0.3rem;
    }

    h1 {
      font-size: 2rem;
    }
  }
`;

export const MenuBox = styled.div`
  position: relative;
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

type DropdownContainerProps = {
  open: boolean;
};

export const DropdownContainer = styled.div<DropdownContainerProps>`
  overflow: hidden;
  position: absolute;
  top: 50px;
  right: 0;
  font-size: 1.5rem;
  text-align: center;
  z-index: 1;
  max-height: 0;
  transition: max-height 0.6s cubic-bezier(0, 1, 0, 1);

  ${({ open }) =>
    open &&
    css`
      max-height: 1000px;
      transition: max-height 1.4s cubic-bezier(0.25, 0.25, 0.75, 0.75);
    `}

  box-shadow: rgb(0 0 0 / 30%) 0px 0px 6px;
  background: ${({ theme }) => theme.colors.white_50};
`;

export const DropdownItem = styled.div`
  padding: 1rem 2rem;
  cursor: pointer;

  :hover {
    background: ${({ theme }) => theme.colors.gray_100};
    font-weight: 600;
  }
`;
