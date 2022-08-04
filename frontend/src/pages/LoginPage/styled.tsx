import { TernokoMemberNickname } from '.';

import styled, { css, keyframes } from 'styled-components';

export const Box = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;

  @media ${({ theme }) => theme.devices.tablet} {
    flex-direction: column;
  }
`;

export const LeftBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 45%;
  height: 100%;

  @media ${({ theme }) => theme.devices.tablet} {
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

  @media ${({ theme }) => theme.devices.labtop} {
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

export const RightBox = styled.div`
  position: relative;
  width: 55%;
  height: 100%;

  background-color: ${({ theme }) => theme.colors.gray_100};

  @media ${({ theme }) => theme.devices.tablet} {
    width: 100%;
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
};

export const TernokoProfile = styled.div<ProfileProps>`
  position: absolute;
  transform: translateX(-50%);
  display: inline-block;
  width: fit-content;
  bottom: -100%;

  img {
    width: 25rem;
    height: 25rem;
  }

  ${({ nickname }) =>
    css`
      animation: ${['앤지', '애쉬', '열음'].includes(nickname) ? toTopToTop : toTopToBottom} 5s
        infinite;
    `}

  ${({ nickname }) =>
    nickname === '앤지' &&
    css`
      left: 30%;
    `};

  ${({ nickname }) =>
    nickname === '애쉬' &&
    css`
      left: 50%;
      animation-delay: 0.4s;
    `};

  ${({ nickname }) =>
    nickname === '열음' &&
    css`
      left: 70%;
      animation-delay: 0.8s;
    `};

  ${({ nickname }) =>
    nickname === '바니' &&
    css`
      left: 20%;
      animation-delay: 1.2s;
    `};

  ${({ nickname }) =>
    nickname === '수달' &&
    css`
      left: 40%;
      animation-delay: 1.6s;
    `};

  ${({ nickname }) =>
    nickname === '아놀드' &&
    css`
      left: 60%;
      animation-delay: 2s;
    `};

  ${({ nickname }) =>
    nickname === '록바' &&
    css`
      left: 80%;
      animation-delay: 2.4s;
    `};
`;
