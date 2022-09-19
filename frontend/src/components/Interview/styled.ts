import styled from 'styled-components';

import { InterviewStatus } from '@/types/domain';

export const Box = styled.div`
  position: relative;

  width: 28rem;
  padding: 1rem 1.5rem 5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_100};
  border-radius: 5px;
  box-shadow: ${({ theme }) => theme.shadows.basic};

  transition: transform 0.2s ease-in-out;

  :hover {
    transform: translateY(-0.5rem);
  }
`;

type TagProps = {
  status: InterviewStatus;
};

export const Tag = styled.div<TagProps>`
  position: absolute;
  right: 0;
  top: 0;
  padding: 0.6rem;
  font-size: 1.2rem;

  color: ${({ theme }) => theme.colors.white_50};
  background-color: ${({ theme, status }) =>
    status === 'CANCELED' ? theme.colors.pink_200 : theme.colors.green_50};
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
