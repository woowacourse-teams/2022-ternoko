import { QueryClient, QueryClientProvider } from 'react-query';

import { ThemeProvider } from 'styled-components';

import AppRoutes from '@/Router/AppRoutes';

import GlobalStyle from '@/Styles/GlobalStyle';
import theme from '@/Styles/theme';

import LoadingProvider from '@/Shared/context/LoadingProvider';
import ToastProvider from '@/Shared/context/ToastProvider';
import UserProvider from '@/Shared/context/UserProvider';

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
