import styled from 'styled-components';

export const Box = styled.div`
  margin-top: 5rem;
`;

export const ProfileBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;

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
  padding-top: 1rem;
`;

export const Info = styled.div`
  display: flex;
  align-items: flex-start;
  gap: 2rem;
  height: 5rem;
  font-size: 2rem;

  label {
    width: 120px;
    font-weight: 600;
  }

  > p {
    width: 100%;
    height: fit-content;
    padding-left: 0.7rem;
    border-bottom: 1px solid ${({ theme }) => theme.colors.gray_150};
  }
`;

export const Input = styled.input`
  width: 100%;
  height: fit-content;
  border-radius: 10px;
  padding: 1rem;

  font-size: 1.6rem;
  outline: none;

  border: 1px solid ${({ theme }) => theme.colors.gray_150};
`;

export const EditModeBox = styled.div`
  width: 100%;
  margin-top: -0.8rem;
`;

export const Textarea = styled.textarea`
  width: 100%;
  height: 70px;
  border-radius: 10px;
  padding: 1rem;

  font-size: 1.6rem;
  resize: none;
  outline: none;

  border: 1px solid ${({ theme }) => theme.colors.gray_150};
`;

export const DescriptionBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 0.5rem;

  font-size: 1.4rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 7rem;

  a {
    width: 47%;
  }
`;
