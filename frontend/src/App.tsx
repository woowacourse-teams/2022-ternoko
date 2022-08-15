import { ThemeProvider } from 'styled-components';

import LoadingProvider from '@/context/LoadingProvider';
import ToastProvider from '@/context/ToastProvider';
import UserProvider from '@/context/UserProvider';

import AppRoutes from '@/AppRoutes';
import GlobalStyle from '@/styles/GlobalStyle';
import theme from '@/styles/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <UserProvider>
        <ToastProvider>
          <LoadingProvider>
            <GlobalStyle />
            <AppRoutes />
          </LoadingProvider>
        </ToastProvider>
      </UserProvider>
    </ThemeProvider>
  );
};

export default App;
