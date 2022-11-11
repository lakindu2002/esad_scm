import type { AppProps } from 'next/app'
import { ThemeProvider } from '@mui/material'
import { createTheme } from '@mui/material/styles'
import { Toaster } from 'react-hot-toast';
import { Provider as ReduxProvider } from 'react-redux';
import { store } from '../src/store';

function MyApp({ Component, pageProps }: AppProps) {
  const theme = createTheme();
  return (
    <ReduxProvider store={store}>
      <ThemeProvider theme={theme}>
        <Toaster />
        <Component {...pageProps} />
      </ThemeProvider>
    </ReduxProvider>
  )
}

export default MyApp;
