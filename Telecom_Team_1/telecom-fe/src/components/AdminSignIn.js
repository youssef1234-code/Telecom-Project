import React, { useState, useContext } from "react";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../App";

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
  const [showPassword, setShowPassword] = useState(false);
  const { setAuth } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/login/admin`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ adminId, password }),
        }
      );

      if (response.ok) {
        const data = await response.json();
        console.log("Login successful:", data);
        // Redirect or update state here
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);

        // Update app state
        setAuth({ token: data.token, role: data.role });

        navigate("/admin/customers");
      } else {
        // Check content type and handle appropriately
        const contentType = response.headers.get("Content-Type");
        if (contentType && contentType.includes("application/json")) {
          const errorData = await response.json();
          setError(errorData.message || "Invalid login credentials");
        } else {
          setError("Unexpected error occurred. Please try again.");
        }
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
                {showPassword ? <VisibilityOff /> : <Visibility />}{" "}
                {/* Toggle icons */}
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
        <Button
          variant="text"
          fullWidth
          sx={{ mt: 2 }}
          onClick={() => navigate("/customer")} // Navigate to admin sign-in
        >
          Go to Customer Sign In
        </Button>
      </Box>
    </Grid>
  );
};

export default AdminSignIn;
