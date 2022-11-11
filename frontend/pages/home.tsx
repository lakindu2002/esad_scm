import { alpha, Avatar, Box, Typography } from '@mui/material'
import type { NextPage } from 'next'
import Head from 'next/head'
import React from 'react'
import { withMainLayout } from '../src/hocs/with-main-layout'
import AppsIcon from '@mui/icons-material/Apps';
import { withAuthGuard } from '../src/hocs/with-auth-guard'

const Home: NextPage = () => {
  return (
    <>
      <Head>
        <title>Home | SCM</title>
      </Head>
      <Box sx={{
        my: 25,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
      }}>
        <Box sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}>
          <Avatar
            sx={{
              background: (theme) => alpha(theme.palette.primary.main, 0.8),
              mb: 2,
              width: 75,
              height: 75
            }}
          >
            <AppsIcon fontSize="large" />
          </Avatar>
          <Typography variant="body1"
            fontSize={22}
            color="text.primary"
          >
            Select An Application To Get Started.
          </Typography>
        </Box>
      </Box>
    </>
  )
}

export default withAuthGuard(withMainLayout(Home));
