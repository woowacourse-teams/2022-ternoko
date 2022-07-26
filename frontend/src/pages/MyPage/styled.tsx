import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 5rem;
`;

export const ProfileBox = styled.div`
  width: fit-content;

  img {
    width: 160px;
    height: 160px;
    border-radius: 100%;
    object-fit: cover;
  }
`;

export const InfoBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  width: 69%;
  padding-top: 4rem;
`;

export const Info = styled.div`
  display: flex;
  gap: 2rem;
  font-size: 2rem;

  label {
    width: 120px;
    font-weight: 600;
  }

  p {
    width: 100%;
    padding: 0 0 0.3rem 0.7rem;
    border-bottom: 1px solid ${({ theme }) => theme.colors.gray_150};
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 3rem;
`;
