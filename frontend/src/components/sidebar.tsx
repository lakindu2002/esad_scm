import { Box, Button, Divider, Drawer, Typography } from "@mui/material";
import { FC } from "react";
import NextLink from 'next/link';
import { useRouter } from "next/router";

const items: { title: string; href: string }[] = [
  {
    title: "Inventory",
    href: "/inventory"
  },
  {
    title: "Fleet",
    href: "/fleet"
  },
];

interface SidebarProps { }

export const Sidebar: FC<SidebarProps> = () => {
  const router = useRouter();
  const handleLogoutClick = () => {
    localStorage.removeItem("token");
    router.push("/");
  }
  return (
    <Drawer
      anchor="left"
      variant="permanent"
      open
      PaperProps={{
        sx: {
          width: 250,
          background: (theme) => theme.palette.grey[300],
        },
      }}
    >
      <Box sx={{ my: 2 }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
          }}
        >
          <Typography variant="h1" fontSize={30} fontWeight={600}>
            SCM
          </Typography>
        </Box>
        <Divider
          sx={{
            my: 2,
            borderColor: "#FFF",
          }}
        />
        <Box sx={{
          px: 3
        }}>
          <Typography variant="overline"
            fontWeight={600}
          >
            Applications
          </Typography>
          {items.map((item, itemIdx) => (
            <Box
              key={itemIdx}
              sx={{ my: 2 }}
            >
              <NextLink
                href={item.href}
                passHref
              >
                <Button
                  fullWidth
                  color="primary"
                  variant="contained"
                >
                  {item.title}
                </Button>
              </NextLink>
            </Box>
          ))}
          <Divider sx={{ my: 2 }} />
          <Button
            fullWidth
            color="error"
            variant="contained"
            onClick={handleLogoutClick}
          >
            Logout
          </Button>
        </Box>
      </Box>
    </Drawer>
  );
};
