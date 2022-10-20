import styled from 'styled-components';

import theme from '@/Styles/theme';

export const additionalFrameStyle = `
  @media ${theme.devices.laptop(200)} {
    width: 60%;
  };

  @media ${theme.devices.tablet()} {
    width: 70%;
  };

  @media ${theme.devices.tabletM()} {
    width: 90%;
  };
`;

export const Box = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.5rem;
`;

export const FullDate = styled.div`
  margin-bottom: 1rem;

  font-size: 1.7rem;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.pink_150};
`;

export const TimeContainer = styled.div`
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
`;

export const Time = styled.span`
  padding: 0.6rem;

  border: 1px solid ${({ theme }) => theme.colors.gray_150};
  border-radius: 10px;
  font-weight: 500;
`;

export const EmptyMessage = styled.p`
  text-align: center;
  font-size: 1.8rem;
  font-weight: 500;
`;
