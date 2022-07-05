import styled from 'styled-components';

export const Box = styled.div`
  width: fit-content;
`;

export const CoachProfileImage = styled.img`
  width: 90px;
  height: 90px;
  border-radius: 25px;
  margin-bottom: 0.5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray};
`;

export const CoachName = styled.p`
  text-align: center;
  font-size: 1.2rem;
  font-weight: bold;
`;
