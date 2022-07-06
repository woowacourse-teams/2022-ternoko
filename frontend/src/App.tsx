import React from 'react';
import styled, { ThemeProvider } from 'styled-components';

import GlobalStyle from './styles/GlobalStyle';
import theme from './styles/theme';

import HomePage from './pages/HomePage';
import Header from './components/Header';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Layout>
        <HomePage />
      </Layout>
    </ThemeProvider>
  );
};

type LayoutProps = {
  children: React.ReactNode;
};

const Layout = ({ children }: LayoutProps) => {
  return (
    <>
      <Header />
      <S.Body>{children}</S.Body>
    </>
  );
};

const S = {
  Body: styled.div`
    min-height: calc(100% - 60px);
    padding: 3rem 9rem 0;
  `,
};

export default App;
