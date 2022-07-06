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
  .subTitle {
    display: flex;
    align-items: center;
    gap: 1rem;
    margin-bottom: 2rem;

    font-weight: bold;

    h3 {
      font-size: 1.3rem;
    }
  }

  ${({ stepStatus }) =>
    stepStatus === 'show' &&
    css`
      overflow: visible;
      opacity: 1;
      border: 3px solid yellow;

      .fold-box {
        max-height: 1000px;
      }
    `}

  ${({ stepStatus }) =>
    stepStatus === 'hidden' &&
    css`
      max-height: 0;
      opacity: 0;
      border: 3px solid yellow;
    `}

  ${({ stepStatus }) =>
    stepStatus === 'onlyShowTitle' &&
    css`
      overflow: visible;
      opacity: 1;
      border: 3px solid yellow;

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

  h2 {
    position: relative;
    font-size: 3rem;
    cursor: pointer;
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

  background-color: ${({ theme }) => theme.colors.pink};
  color: ${({ theme }) => theme.colors.white};
`;

export const Bar = styled.div`
  width: 1px;
  height: 40px;
  margin-left: 10px;
  background: ${({ theme }) => theme.colors.pink};
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2.5rem;
`;
