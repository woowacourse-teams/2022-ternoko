import styled, { css } from 'styled-components';

export const TitleBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;

  h2 {
    font-size: 3rem;
    cursor: pointer;
  }
`;

export const TabMenuBox = styled.div`
  display: flex;
  gap: 2rem;
`;

type TabMenuProps = {
  active: boolean;
};

export const TabMenu = styled.h3<TabMenuProps>`
  position: relative;
  font-size: 2rem;
  cursor: pointer;

  &:hover:after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -3px;
    width: 100%;
    height: 3px;

    background-color: ${({ theme }) => theme.colors.pink_200};
  }

  ${({ active }) =>
    active &&
    css`
      &:after {
        content: '';
        position: absolute;
        left: 0;
        bottom: -3px;
        width: 100%;
        height: 3px;

        background-color: ${({ theme }) => theme.colors.pink_200};
      }
    `}
`;
