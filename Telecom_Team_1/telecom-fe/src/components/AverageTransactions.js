import React, { useState } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl, Button, Alert, Typography, Box } from "@mui/material";
import GenericTable from "./GenericTable";

const AverageTransactions = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({walletID: "", startDate: "", endDate: ""});
  const [error, setError] = useState("");  // State to store error message

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/average-transactions`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          walletID: filters.walletID,
          startDate: filters.startDate,
          endDate: filters.endDate,
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
      setData(json);
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
          label="Wallet ID"
          variant="outlined"
          value={filters.walletID}
          onChange={(e) => handleFilterChange("walletID", e.target.value)}
          fullWidth
        />
        <TextField
          label="Start Date"
          variant="outlined"
          type="date"
          value={filters.startDate}
          onChange={(e) => handleFilterChange("startDate", e.target.value)}
          fullWidth
          InputLabelProps={{
            shrink: true,
          }}
        />
        <TextField
          label="End Date"
          variant="outlined"
          type="date"
          value={filters.endDate}
          onChange={(e) => handleFilterChange("endDate", e.target.value)}
          fullWidth
          InputLabelProps={{
            shrink: true,
          }}
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

      {/* Generic Table */}
      <Box
      sx={{
        flex: 1, // Fills available vertical space
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fafafa",
        padding: 2,
      }}>
        <Typography variant = "h4" gutterBottom>
            {data}
        </Typography>
      </Box>
    </Paper>
  );
};

export default AverageTransactions;
