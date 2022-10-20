import { Colors, Devices, Shadows } from '@/types/styles/theme';

import 'styled-components';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: Colors;
    shadows: Shadows;
    devices: Devices;
  }
}
