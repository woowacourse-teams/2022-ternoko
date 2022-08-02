import 'styled-components';

import { Colors, Devices, Shadows } from '@/types/styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: Colors;
    shadows: Shadows;
    devices: Devices;
  }
}
