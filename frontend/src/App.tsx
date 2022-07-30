import { ThemeProvider } from 'styled-components';

import AppRoutes from '@/AppRoutes';
import GlobalStyle from '@/styles/GlobalStyle';
import theme from '@/styles/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <AppRoutes />
    </ThemeProvider>
  );
};

export default App;
