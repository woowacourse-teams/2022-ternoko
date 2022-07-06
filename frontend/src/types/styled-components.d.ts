import 'styled-components';

import { Colors } from '../styles/theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: Colors;
  }
}
