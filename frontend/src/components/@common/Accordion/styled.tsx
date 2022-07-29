import styled, { css } from 'styled-components';

type ToggleProps = {
  open: boolean;
};

export const Box = styled.div`
  position: relative;
  width: 50rem;

  background: ${({ theme }) => theme.colors.white_50};
  box-shadow: ${({ theme }) => theme.shadows.basic};
  border-radius: 5px;

  :after {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 5px;
    height: 100%;

    background: ${({ theme }) => theme.colors.pink_100};
    border-radius: 5px 0 0 5px;
  }
`;

export const Title = styled.h3<ToggleProps>`
  transition: font-weight 0.3s cubic-bezier(0.25, 0.25, 0.75, 0.75);
  font-weight: ${({ open }) => (open ? 700 : 400)};
`;

export const Icon = styled.div<ToggleProps>`
  position: relative;
  width: 2.5rem;
  height: 2.5rem;
  padding: 5px;
  transition: all 0.3s cubic-bezier(0.25, 0.25, 0.75, 0.75);

  background: ${({ theme }) => theme.colors.gray_200};
  border-radius: 100%;

  ${({ open }) =>
    open &&
    css`
      transform: rotate(-180deg);
      transition: all 0.3s cubic-bezier(0.25, 0.25, 0.75, 0.75);
      background: ${({ theme }) => theme.colors.pink_100};
    `}

  :after {
    content: '';
    position: absolute;
    top: 0.5rem;
    left: 0.6rem;
    width: 1.2rem;
    height: 1.2rem;
    transform: rotate(-135deg);

    border-top: 3px solid ${({ theme }) => theme.colors.white_50};
    border-left: 3px solid ${({ theme }) => theme.colors.white_50};
  }
`;

export const Header = styled.div<ToggleProps>`
  display: flex;
  justify-content: space-between;
  padding: 2rem;
  cursor: pointer;

  :hover ${Icon} {
    background: ${({ theme, open }) => (open ? theme.colors.gray_200 : theme.colors.pink_100)};
  }
`;

export const Content = styled.div<ToggleProps>`
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s cubic-bezier(0, 1, 0, 1);

  ${({ open }) =>
    open &&
    css`
      max-height: 1000px;
      transition: max-height 0.7s cubic-bezier(0.25, 0.25, 0.75, 0.75);
    `}
`;

export const Description = styled.p`
  padding: 0 2rem 2rem;
`;
