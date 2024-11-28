import React from "react";
import { Box, Typography, Button, Container } from "@mui/material";
import { useNavigate } from "react-router-dom";

const NotFoundPage = () => {
  const navigate = useNavigate();

  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "100vh",
        textAlign: "center",
        backgroundColor: "#f9f9f9",
      }}
    >
      <Typography variant="h1" color="primary" sx={{ fontWeight: "bold", mb: 2 }}>
        404
      </Typography>
      <Typography variant="h5" color="textSecondary" sx={{ mb: 2 }}>
        Oops! The page you're looking for doesn't exist.
      </Typography>
      <Typography variant="body1" color="textSecondary" sx={{ mb: 4 }}>
        It seems you may have taken a wrong turn. Let's get you back on track!
      </Typography>
      <Box
        component="img"
        src="https://img.freepik.com/free-vector/404-error-web-template-flat-style_23-2147781022.jpg" // Replace with your 404 illustration URL
        alt="404 Illustration"
        sx={{ maxWidth: "300px", height: "auto", mb: 4 }}
      />
      <Button
        variant="contained"
        color="primary"
        size="large"
        onClick={() => navigate("/")} // Redirect to home page
        sx={{ textTransform: "none" }}
      >
        Go to Homepage
      </Button>
    </Container>
  );
};

export default NotFoundPage;
