import { StepStatus } from '.';

import styled, { css, keyframes } from 'styled-components';

import ScrollContainer from '@/components/@common/ScrollContainer/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

type BoxProps = {
  stepStatus: StepStatus;
};

export const Body = styled.div`
  height: calc(100% - 60px);

  padding: 3rem 30rem 0;

  @media ${({ theme }) => theme.devices.laptopL()} {
    padding: 3rem 25rem 0;
  }

  @media ${({ theme }) => theme.devices.laptop(200)} {
    padding: 3rem 19rem 0;
  }

  @media ${({ theme }) => theme.devices.laptop()} {
    padding: 3rem 5rem 0;
  }

  @media ${({ theme }) => theme.devices.tabletM(60)} {
    padding: 2rem 3rem 0;
  }

  @media ${({ theme }) => theme.devices.mobileL()} {
    padding: 2rem 2rem 0;
  }

  @media ${({ theme }) => theme.devices.mobileM()} {
    padding: 2rem 1rem 0;
  }
`;

export const Box = styled.div<BoxProps>`
  position: relative;
  overflow: hidden;

  .sub-title {
    display: flex;
    align-items: center;
    width: fit-content;
    gap: 1rem;
    margin-bottom: 1.8rem;

    font-weight: bold;
    cursor: pointer;

    h3 {
      font-size: 1.6rem;
    }
  }

  .fold-box {
    overflow: hidden;
    margin-top: 2rem;
  }

  ${({ stepStatus }) =>
    stepStatus === 'show' &&
    css`
      visibility: visible;
      opacity: 1;
      z-index: 1;

      .fold-box {
        max-height: 1000px;
        transition: max-height 0.5s cubic-bezier(0.25, 0.25, 0.75, 0.75);
      }
    `}

  ${({ stepStatus }) =>
    stepStatus === 'hidden' &&
    css`
      visibility: hidden;
    `}

  ${({ stepStatus }) =>
    stepStatus === 'onlyShowTitle' &&
    css`
      visibility: visible;

      .sub-title {
        opacity: 0.4;
      }

      .sub-title:after {
        content: '';
        position: absolute;
        left: 12px;
        top: 33px;
        width: 2px;
        height: 25px;

        background-color: ${({ theme }) => theme.colors.pink_200};
        opacity: 1;
      }

      .fold-box {
        max-height: 0;
        transition: max-height 0.4s cubic-bezier(0, 1, 1, 1);
      }
    `}
`;

export const TitleBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;

  a {
    margin-right: 1rem;
  }

  h2 {
    position: relative;
    font-size: 3rem;
  }
`;

export const SubTitleBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 2rem;

  font-weight: bold;

  h3 {
    font-size: 1.3rem;
  }
`;

export const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 25px;
  height: 25px;
  font-size: 1.4rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.pink_200};
  color: ${({ theme }) => theme.colors.white_50};
`;

export const Bar = styled.div`
  width: 1px;
  height: 40px;
  margin-left: 10px;
  background: ${({ theme }) => theme.colors.pink_200};
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2.5rem;
`;

export const DateBox = styled.div`
  display: flex;
  gap: 3rem;
  padding-bottom: 3rem;

  @media ${({ theme }) => theme.devices.tabletM()} {
    flex-direction: column;
  }
`;

const toLeft = keyframes`
  0% {
    transform: translateX(50%);
    opacity: 0;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
`;

type TimeContainerProps = {
  heightUnit: number;
};

export const TimeContainer = styled(ScrollContainer)<TimeContainerProps>`
  animation: ${toLeft} 1s;

  height: ${({ heightUnit }) => Math.min(47, heightUnit * 8)}rem;
`;

export const DisplayClickedText = styled.span`
  font-size: 1.3rem;
  margin-left: 1rem;

  color: ${({ theme }) => theme.colors.pink_200};
`;
