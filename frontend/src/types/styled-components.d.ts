import 'styled-components';

import { Colors, Shadows } from '@/types/styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: Colors;
    shadows: Shadows;
  }
}
