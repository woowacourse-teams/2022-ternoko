import styled from 'styled-components';

export const Box = styled.div`
  position: relative;
  width: 25rem;
  padding: 1rem 1.5rem 1.5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_100};
  border-radius: 5px;
  box-shadow: ${({ theme }) => theme.shadows.basic};
`;

export const ImageTextBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 10px;
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
`;

export const ButtonImage = styled.img`
  width: 13px;
  height: 13px;
`;
