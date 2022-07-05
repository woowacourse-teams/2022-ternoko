import styled from 'styled-components';

export const Box = styled.div`
  width: fit-content;
  cursor: pointer;
`;

export const CoachProfileImage = styled.img`
  width: 110px;
  height: 110px;
  border-radius: 25px;
  margin-bottom: 0.5rem;

  border: 1px solid ${({ theme }) => theme.colors.gray};
`;

export const CoachName = styled.p`
  text-align: center;
  font-size: 1.2rem;
  font-weight: bold;
`;
