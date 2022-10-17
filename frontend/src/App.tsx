import { QueryClient, QueryClientProvider } from 'react-query';

import { ThemeProvider } from 'styled-components';

import AppRoutes from '@/AppRoutes';
import LoadingProvider from '@/common/context/LoadingProvider';
import ToastProvider from '@/common/context/ToastProvider';
import UserProvider from '@/common/context/UserProvider';
import GlobalStyle from '@/common/styles/GlobalStyle';
import theme from '@/common/styles/theme';

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
