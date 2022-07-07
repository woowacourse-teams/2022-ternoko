import styled, { css } from 'styled-components';
import { StepStatus } from '.';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
`;

type BoxProps = {
  stepStatus: StepStatus;
};

export const Box = styled.div<BoxProps>`
  position: relative;

  .sub-title {
    display: flex;
    align-items: center;
    width: fit-content;
    gap: 1rem;
    margin-bottom: 2rem;

    font-weight: bold;
    cursor: pointer;

    h3 {
      font-size: 1.3rem;
    }
  }

  ${({ stepStatus }) =>
    stepStatus === 'show' &&
    css`
      overflow: visible;
      opacity: 1;
      z-index: 1;

      .fold-box {
        max-height: 1000px;
      }
    `}

  ${({ stepStatus }) =>
    stepStatus === 'hidden' &&
    css`
      max-height: 0;
      opacity: 0;
    `}

  ${({ stepStatus }) =>
    stepStatus === 'onlyShowTitle' &&
    css`
      overflow: visible;
      opacity: 1;

      .sub-title {
        opacity: 0.4;
      }

      .sub-title:after {
        content: '';
        position: absolute;
        left: 9px;
        top: 33px;
        width: 2px;
        height: 25px;

        background-color: ${({ theme }) => theme.colors.pink_200};
        opacity: 1;
      }

      .fold-box {
        max-height: 0;
        overflow: hidden;
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
  width: 20px;
  height: 20px;
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
`;

export const TimeContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  height: 42rem;

  overflow-y: scroll;

  padding-right: 3rem;
`;

type TimeProps = {
  active: boolean;
};

export const Time = styled.div<TimeProps>`
  padding: 1.5rem 5rem;
  color: ${({ theme }) => theme.colors.pink_200};
  border: 1px solid ${({ theme }) => theme.colors.pink_50};
  font-weight: 600;
  font-size: 1.2rem;
  cursor: pointer;

  :hover {
    border: 1px solid ${({ theme }) => theme.colors.pink_300};
  }

  ${({ active }) =>
    active &&
    css`
      border: 1px solid ${({ theme }) => theme.colors.pink_300};
    `}
`;
