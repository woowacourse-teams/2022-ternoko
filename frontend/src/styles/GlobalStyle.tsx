import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  html {
    width: 100%;
    height: 100%;
    font-size: 62.5%;
    box-sizing: border-box;
  }

  body {
    width: 100%;
    height: 100%;
    background-color : ${({ theme }) => theme.colors.gray_50}
  }

  #root{
    height:100%;
  }

  *, *:before, *:after {
    padding: 0;
    margin: 0;
    box-sizing: inherit;
  }
  
  a {
    text-decoration: none;
    color: inherit;
  }

  @font-face {
    font-family: 'EarlyFontDiary';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_220508@1.0/EarlyFontDiary.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
  }
`;

export default GlobalStyle;
