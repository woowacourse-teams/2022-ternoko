import ToastProvider from './context/ToastProvider';

import { ThemeProvider } from 'styled-components';

import UserProvider from '@/context/UserProvider';

import AppRoutes from '@/AppRoutes';
import GlobalStyle from '@/styles/GlobalStyle';
import theme from '@/styles/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <UserProvider>
        <ToastProvider>
          <GlobalStyle />
          <AppRoutes />
        </ToastProvider>
      </UserProvider>
    </ThemeProvider>
  );
};

export default App;
