import React, { useState } from "react";
import { Paper, TextField, Button, Alert, Typography, Box } from "@mui/material";
const CashbackTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ mobileNum: "", nationalId: "" });
  const [error, setError] = useState("");

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    // Validate input
    try {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/cashbacks`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            //mobileNum: filters.mobileNum,
            nId: filters.nationalId,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        console.log("error"+ errorData);
        setData("");
        setError(errorData.message || "An error occurred. Please try again.");
        return;
      }

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
      {/* Filters */}
      <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
        {/* <TextField
          label="Mobile Number"
          variant="outlined"
          value={localStorage.getItem("mobileNumber") || ""}
          onChange={(e) =>
            handleFilterChange("mobileNum", e.target.value)
          }
          fullWidth
          InputLabelProps={{
            shrink: true,
          }}
          disabled={true} // Disable field to only show the stored value
        /> */}
        <TextField
          label="National ID"
          variant="outlined"
          value={filters.nationalId}
          onChange={(e) => handleFilterChange("nationalId", e.target.value)}
          fullWidth
        />
        <Button variant="contained" color="primary" onClick={handleSubmit}>
          Fetch Data
        </Button>
      </div>

      {/* Show error alert if any */}
      {error && (
        <Alert severity="error" style={{ marginBottom: "20px" }}>
          {error}
        </Alert>
      )}

      {/* Show data */}
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

export default CashbackTable;