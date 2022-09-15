import styled from 'styled-components';

import ScrollContainer from '@/components/@common/ScrollContainer/styled';

import Time from '@/components/Time/styled';

export const Box = styled.div`
  width: fit-content;
`;

export const DateBox = styled.div`
  display: flex;
  gap: 3rem;
  padding-bottom: 3rem;

  @media ${({ theme }) => theme.devices.tabletM()} {
    flex-direction: column;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding-right: 2.5rem;

  > * {
    width: 48%;
  }
`;

export const TimeContainer = styled(ScrollContainer)`
  ${Time}:not(:first-child) {
    border-top: none;
  }
`;
