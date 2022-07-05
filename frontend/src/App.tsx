import React from 'react';
import styled, { ThemeProvider } from 'styled-components';

import GlobalStyle from './styles/GlobalStyle';
import theme from './styles/theme';

import Header from './components/Header';
import Reservation from './components/Reservation';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Layout>
        <Reservation />
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
    height: calc(100% - 9rem);
    padding: 3rem 9rem 0;
  `,
};

export default App;
