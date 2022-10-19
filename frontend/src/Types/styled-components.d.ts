import 'styled-components';

import theme from '@/Styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: { [K in keyof typeof theme.colors]: typeof theme.colors[K] };
    shadows: { [K in keyof typeof theme.shadows]: typeof theme.shadows[K] };
    devices: { [K in keyof typeof theme.devices]: typeof theme.devices[K] };
  }
}
