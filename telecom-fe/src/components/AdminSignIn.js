import React, { useState } from "react";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

import {
  Box,
  TextField,
  Button,
  Typography,
  CircularProgress,
  Grid,
  IconButton,
} from "@mui/material";


const AdminSignIn = () => {
  const [adminId, setAdminId] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false); // State to toggle password visibility

  const handleLogin = async () => {
    setLoading(true);
    setError(""); // Clear any previous error

    try {
      const response = await fetch("http://localhost:8082/api/login/admin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ adminId, password }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Login successful:", data);
        // Redirect or update state here
      } else {
        const errorData = await response.json();
        setError(errorData.message || "Invalid login credentials");
      }
    } catch (err) {
      setError("Failed to connect to the server. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      style={{ minHeight: "100vh", backgroundColor: "#f5f5f5" }}
    >
      <Box
        sx={{
          width: 400,
          padding: 4,
          borderRadius: 2,
          boxShadow: 3,
          backgroundColor: "white",
        }}
      >
        <Typography variant="h5" gutterBottom align="center">
          Admin Sign In
        </Typography>
        <TextField
          label="UserName"
          variant="outlined"
          fullWidth
          margin="normal"
          value={adminId}
          onChange={(e) => setAdminId(e.target.value)}
        />
        <TextField
          label="Password"
          variant="outlined"
          type={showPassword ? "text" : "password"} // Toggle between text and password type
          fullWidth
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          InputProps={{
            endAdornment: (
              <IconButton
                onClick={() => setShowPassword((prev) => !prev)} // Toggle password visibility
                edge="end"
              >
                {showPassword ? <VisibilityOff /> : <Visibility />} {/* Toggle icons */}
              </IconButton>
            ),
          }}
        />
        {error && (
          <Typography color="error" variant="body2" sx={{ mt: 2 }}>
            {error}
          </Typography>
        )}
        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          onClick={handleLogin}
          disabled={loading}
        >
          {loading ? <CircularProgress size={24} color="inherit" /> : "Sign In"}
        </Button>
      </Box>
    </Grid>
  );
};

export default AdminSignIn;