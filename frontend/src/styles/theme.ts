const sizes = {
  mobileS: '320px',
  mobileM: '375px',
  mobileL: '425px',
  tabletM: '590px',
  tablet: '768px',
  laptop: '1024px',
  laptopL: '1440px',
  desktop: '2560px',
};

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
    pink_200: '#ff385c',
    pink_300: '#f03e3e',
    orange: '#ffd24d4d',
    purple: '#4a154b',
  },
  shadows: {
    basic: '#64646f33 0px 7px 29px 0px',
  },
  devices: {
    mobileS: `(max-width: ${sizes.mobileS})`,
    mobileM: `(max-width: ${sizes.mobileM})`,
    mobileL: `(max-width: ${sizes.mobileL})`,
    tabletM: `(max-width: ${sizes.tabletM})`,
    tablet: `(max-width: ${sizes.tablet})`,
    laptop: `(max-width: ${sizes.laptop})`,
    laptopL: `(max-width: ${sizes.laptopL})`,
    desktop: `(max-width: ${sizes.desktop})`,
  },
};

export type Colors = typeof theme.colors;
export type Shadows = typeof theme.shadows;
export type Devices = typeof theme.devices;

export default theme;
