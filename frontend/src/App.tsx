import { ThemeProvider } from 'styled-components';

import UserProvider from '@/context/UserProvider';

import AppRoutes from '@/AppRoutes';
import GlobalStyle from '@/styles/GlobalStyle';
import theme from '@/styles/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <UserProvider>
        <GlobalStyle />
        <AppRoutes />
      </UserProvider>
    </ThemeProvider>
  );
};

export default App;
