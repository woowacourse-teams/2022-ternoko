import App from '@/App';

import { createRoot } from 'react-dom/client';

const container = document.getElementById('root');
const root = createRoot(container as Element);

root.render(<App />);
