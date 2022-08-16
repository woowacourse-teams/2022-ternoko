import { TernokoMemberNickname } from '.';

import styled, { css, keyframes } from 'styled-components';

type BoxProps = {
  additionalBoxStyle?: string;
};

export const Box = styled.div<BoxProps>`
  position: relative;
  width: 55%;
  height: 100%;
  overflow: hidden;
  z-index: 5;

  background-color: ${({ theme }) => theme.colors.gray_100};

  @media ${({ theme }) => theme.devices.tablet()} {
    width: 100%;
  }

  ${({ additionalBoxStyle }) => additionalBoxStyle};
`;

const toTopToBottom = keyframes`
  0%,
  60%,
  75%,
  90%,
  to {
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }

  0% {
    bottom: -100%;
  }

  60% {
    bottom: 35%;
  }

  75% {
    bottom: 27%;
  }

  90% {
    bottom: 32%;
  }

  to {
    bottom: 30%;
  }
`;

const toTopToTop = keyframes`
  0%,
  60%,
  75%,
  90%,
  to {
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }

  0% {
    bottom: -100%;
  }

  60% {
    bottom: 55%;
  }

  75% {
    bottom: 47%;
  }

  90% {
    bottom: 52%;
  }

  to {
    bottom: 50%;
  }
`;

type ProfileProps = {
  nickname: TernokoMemberNickname;
  profileSizeRem: number;
  animationDuration: number;
};

export const TernokoProfile = styled.div<ProfileProps>`
  position: absolute;
  transform: translateX(-50%);
  display: inline-block;
  width: fit-content;
  bottom: -100%;

  img {
    width: ${({ profileSizeRem }) => `${profileSizeRem}rem`};
    height: ${({ profileSizeRem }) => `${profileSizeRem}rem`};
  }

  ${({ nickname, animationDuration }) =>
    css`
      animation: ${['앤지', '애쉬', '열음'].includes(nickname) ? toTopToTop : toTopToBottom}
        ${animationDuration}s infinite;
    `}

  ${({ nickname }) =>
    nickname === '앤지' &&
    css`
      left: 30%;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '애쉬' &&
    css`
      left: 50%;
      animation-delay: ${animationDuration * 0.08}s;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '열음' &&
    css`
      left: 70%;
      animation-delay: ${animationDuration * 0.16}s;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '바니' &&
    css`
      left: 20%;
      animation-delay: ${animationDuration * 0.24}s;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '수달' &&
    css`
      left: 40%;
      animation-delay: ${animationDuration * 0.32}s;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '아놀드' &&
    css`
      left: 60%;
      animation-delay: ${animationDuration * 0.4}s;
    `};

  ${({ nickname, animationDuration }) =>
    nickname === '록바' &&
    css`
      left: 80%;
      animation-delay: ${animationDuration * 0.48}s;
    `};
`;
