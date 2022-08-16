import { QueryClient, QueryClientProvider } from 'react-query';

import { ThemeProvider } from 'styled-components';

import LoadingProvider from '@/context/LoadingProvider';
import ToastProvider from '@/context/ToastProvider';
import UserProvider from '@/context/UserProvider';

import AppRoutes from '@/AppRoutes';
import GlobalStyle from '@/styles/GlobalStyle';
import theme from '@/styles/theme';

const queryClient = new QueryClient();

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
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
    </QueryClientProvider>
  );
};

export default App;
