import styled from 'styled-components';

<<<<<<< HEAD
=======
export const TextArea = styled.textarea`
  width: 100%;
  height: 70px;
  border-radius: 10px;
  padding: 1rem;

  font-size: 1.4rem;
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

>>>>>>> 5b45068 (feat: CommentModal - create, read 개발)
export const Icon = styled.img`
  position: absolute;
  top: 1rem;
  right: 1rem;
  width: 2rem;
  height: 2rem;
  object-fit: cover;
  cursor: pointer;
`;

export const ExampleBox = styled.div`
  margin: 1rem 0 3rem;
  color: ${({ theme }) => theme.colors.gray_200};

  > p {
    line-height: 1.5;
  }
`;

export const ButtonBox = styled.div`
  margin-top: 1.5rem;
`;

export const Form = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
`;
