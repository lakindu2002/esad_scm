import { Box, styled } from "@mui/material";
import { FC } from "react";
import { Sidebar } from "../components/sidebar";

interface MainLayoutProps {
  children: React.ReactNode;
}

const MainLayoutRoot = styled('div')(({ theme }) => ({
  display: 'flex',
  flex: '1 1 auto',
  maxWidth: '100%',
  paddingTop: 15,
  height: '100%',
  backgroundColor: theme.palette.background.default,
  fontFamily: theme.typography.fontFamily,
  paddingLeft: 280
}));

export const MainLayout: FC<MainLayoutProps> = ({ children }) => {
  return (
    <>
      <MainLayoutRoot>
        <Box
          sx={{
            display: 'flex',
            flex: '1 1 auto',
            flexDirection: 'column',
            width: '100%',
          }}
        >
          {children}
        </Box>
      </MainLayoutRoot>
      <Sidebar />
    </>
  );
};
