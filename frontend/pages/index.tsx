import { Box, Button, Card, CardContent, Container, Divider, TextField, Typography } from "@mui/material";
import axios from "axios";
import { useFormik } from "formik";
import { NextPage } from "next";
import Head from "next/head";
import { useRouter } from "next/router";
import toast from "react-hot-toast";
import * as Yup from 'yup';
import { API_BASE_URL } from "../src/constants";
import { AuthResponse } from "../src/types/auth";

const LoginPage: NextPage = () => {
  const router = useRouter();
  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    onSubmit: async (values) => {
      try {
        const resp = await axios.post<AuthResponse>(`${API_BASE_URL}/auth/login`, values);
        localStorage.setItem("token", JSON.stringify(resp.data));
        router.push("/home");
      } catch (err) {
        toast.error("Invalid credentials");
      }
    },
    validationSchema: Yup.object({
      email: Yup.string().email("Invalid email address").required("Required"),
      password: Yup.string().required("Required"),
    })
  })
  return (
    <>
      <Head>
        <title>Login | SCM</title>
      </Head>
      <Container maxWidth="sm">
        <form
          noValidate
          onSubmit={formik.handleSubmit}
        >
          <Card sx={{ my: 15 }}>
            <CardContent>
              <Typography variant="h6">
                Login to SCM
              </Typography>
              <Divider sx={{ my: 2 }} />
              <TextField
                label="Email Address"
                name="email"
                onChange={formik.handleChange}
                value={formik.values.email}
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                error={formik.touched.email && Boolean(formik.errors.email)}
                helperText={formik.touched.email && formik.errors.email}
                onBlur={formik.handleBlur}
              />
              <TextField
                label="Password"
                name="password"
                onChange={formik.handleChange}
                value={formik.values.password}
                variant="outlined"
                type={"password"}
                fullWidth
                error={formik.touched.password && Boolean(formik.errors.password)}
                helperText={formik.touched.password && formik.errors.password}
                onBlur={formik.handleBlur}
              />
            </CardContent>
            <Box sx={{ my: 2, width: '100%', display: 'flex', justifyContent: 'center' }}>
              <Button
                type="submit"
                variant="contained"
              >
                Login
              </Button>
            </Box>
          </Card>
        </form>
      </Container>
    </>
  );
};

export default LoginPage;
