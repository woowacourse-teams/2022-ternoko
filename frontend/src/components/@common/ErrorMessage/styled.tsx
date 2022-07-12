import styled from 'styled-components';

const ErrorMessage = styled.p`
  margin-top: 0.5rem;
  color: ${({ theme }) => theme.colors.pink_200};
  font-size: 1.4rem;
`;

export default ErrorMessage;
