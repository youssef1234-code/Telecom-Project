import React, { useState } from "react";
import { Paper, TextField, Box, MenuItem, Select, InputLabel, FormControl, Button, Alert, Typography } from "@mui/material";

const AcceptedTransactionTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ mobileNum: ""});
  const [error, setError] = useState("");  // State to store error message
  const [count, setCount] = useState(""); 
  const [total, setTotal] = useState("");

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };



  const handleSubmit = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/accepted-transactions`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          mobileNum: filters.mobileNum,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        setData("");
        setError(errorData.message || "An error occurred. Please try again.");  // Handle the error message from the backend
        return;
      }

      // Clear any previous error if request is successful
      setError("");
      
      const json = await response.json();

      if(json != null){
      setCount(json.paymentCount || null);
      setTotal(json.totalPoints || null);
      } else throw error

    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    } 
  };



  return (
    <Paper
      style={{
        padding: 20,
        width: "100%",
        height: "100%",
        boxSizing: "border-box",
      }}
    >
      <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
        <TextField
          label="Mobile Number"
          variant="outlined"
          value={filters.mobileNum}
          onChange={(e) => handleFilterChange("mobileNum", e.target.value)}
          fullWidth
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSubmit}
        >
          Fetch Data
        </Button>
      </div>

      {/* Show error alert if any */}
      {error && (
        <Alert severity="error" style={{ marginBottom: "20px" }}>
          {error}
        </Alert>
      )}

<div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
      <Box
      sx={{
        flex: 1, 
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fafafa",
        padding: 2,
      }}>
        <Typography variant = "h3" gutterBottom>
            Payment Count = {count}
        </Typography>
      </Box>

      <Box
      sx={{
        flex: 1,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fafafa",
        padding: 2,
      }}>
        <Typography variant = "h3" gutterBottom>
            Total Points = {total}
        </Typography>
      </Box>
        
      </div>

      
    </Paper>
  );
};

export default AcceptedTransactionTable;
