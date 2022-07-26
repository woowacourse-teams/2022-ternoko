import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  align-items: center;
  height: calc(100vh - 90px);
`;

export const Form = styled.form`
  display: flex;
  justify-content: space-between;
  width: 100%;
  min-height: calc(100% - 35rem);
`;

export const LeftBox = styled.div`
  width: 44%;
`;

export const IntroduceBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  margin-bottom: 4rem;

  h3 {
    font-size: 2.5rem;
  }

  h4 {
    font-size: 1.5rem;
    font-weight: 400;
    color: ${({ theme }) => theme.colors.gray_200};
  }
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
`;

export const RightBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 44%;
`;

export const ProfileBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.3rem;

  img {
    width: 160px;
    height: 160px;
    border-radius: 100%;
    object-fit: cover;
  }
`;

export const Nickname = styled.p`
  font-size: 2.7rem;
  font-weight: 700;
`;

export const Introduce = styled.p`
  font-size: 1.5rem;
`;
