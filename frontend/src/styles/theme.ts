const theme = {
  colors: {
    white_50: '#ffffff',
    white_100: '#edf0f5',
    black_50: '#00000090',
    black_200: '#000000',
    gray_50: '#f6f6f6',
    gray_100: '#e9ebee',
    gray_150: '#c3c2c8',
    gray_200: '#808080',
    pink_50: '#ffe3e3',
    pink_100: '#ffb7d2',
    pink_150: '#fa86c4',
    pink_200: '#ff385c',
    pink_300: '#f03e3e',
    orange: '#ffd24d4d',
    purple: '#4a154b',
    green_50: '#6dd5a7',
    green_100: '#37b24d',
    red_100: '#c74244',
  },
  shadows: {
    basic: '#64646f33 0px 7px 29px 0px',
    allRound: 'rgb(173 181 189) 0px 0px 5px',
  },
  devices: {
    mobileS: (adjustment: number = 0) => `(max-width: ${320 + adjustment}px)`,
    mobileM: (adjustment: number = 0) => `(max-width: ${375 + adjustment}px)`,
    mobileL: (adjustment: number = 0) => `(max-width: ${425 + adjustment}px)`,
    tabletM: (adjustment: number = 0) => `(max-width: ${590 + adjustment}px)`,
    tablet: (adjustment: number = 0) => `(max-width: ${768 + adjustment}px)`,
    laptop: (adjustment: number = 0) => `(max-width: ${1024 + adjustment}px)`,
    laptopL: (adjustment: number = 0) => `(max-width: ${1440 + adjustment}px)`,
    desktop: (adjustment: number = 0) => `(max-width: ${2560 + adjustment}px)`,
  },
};

export type Colors = typeof theme.colors;
export type Shadows = typeof theme.shadows;
export type Devices = typeof theme.devices;

export default theme;
