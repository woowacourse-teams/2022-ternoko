const theme = {
  colors: {
    white_50: '#ffffff',
    white_100: '#edf0f5',
    black: '#000000',
    gray_50: '#f6f6f6',
    gray_100: '#e9ebee',
    gray_150: '#c3c2c8',
    pink_50: '#ffe3e3',
    pink_200: '#ff385c',
    pink_300: '#f03e3e',
    orange: 'rgba(255, 210, 77, 0.3)',
  },
  shadows: {
    basic: 'rgba(100, 100, 111, 0.2) 0px 7px 29px 0px',
  },
};

export type Colors = typeof theme.colors;
export type Shadows = typeof theme.shadows;

export default theme;
