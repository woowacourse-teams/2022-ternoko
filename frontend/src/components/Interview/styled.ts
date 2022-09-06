import styled, { css } from 'styled-components';

import { InterviewStatus } from '@/types/domain';

type BoxProps = {
  status: InterviewStatus;
};

export const Box = styled.div<BoxProps>`
  position: relative;
  width: 28rem;
  padding: 1rem 1.5rem 5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_100};
  border-radius: 5px;
  box-shadow: ${({ theme }) => theme.shadows.basic};

  transition: transform 0.2s ease-in-out;

  ${({ theme, status }) =>
    status === 'CANCELED' &&
    css`
      background: ${theme.colors.pink_100};
    `}

  :hover {
    transform: translateY(-0.5rem);
  }
`;

export const ImageTextBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 10px;
`;

export const CoachName = styled.p`
  font-size: 1.2rem;
  font-weight: 600;
`;

export const ProfileImage = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 100%;
`;

export const IconImage = styled.img`
  width: 15px;
  height: 15px;
`;

export const ButtonBox = styled.div`
  position: absolute;
  right: 0.7rem;
  bottom: 0.7rem;
  display: flex;
  gap: 1rem;
`;

export const ButtonImage = styled.img`
  width: 13px;
  height: 13px;
`;
