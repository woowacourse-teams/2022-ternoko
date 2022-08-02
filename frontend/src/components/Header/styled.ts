import styled from 'styled-components';

export const Box = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 30rem;

  border: 1px solid red;

  h1 {
    font-family: 'EarlyFontDiary';
    font-size: 3em;
  }

  a {
    display: flex;
    align-items: center;
    gap: 1rem;

    > img {
      width: 50px;
    }
  }
`;

export const MenuBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1.5rem;
`;

export const ProfileImage = styled.img`
  width: 45px;
  height: 45px;
  object-fit: cover;

  border-radius: 100%;
`;

export const Nickname = styled.p`
  font-size: 1.3rem;
`;
