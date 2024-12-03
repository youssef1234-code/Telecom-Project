import React, { useState } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl, Button, Alert, Typography, Box } from "@mui/material";
import GenericTable from "./GenericTable";

const WalletLinking = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({mobileNum: ""});
  const [error, setError] = useState("");  // State to store error message
  

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/wallet-linking`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          mobileNum: filters.mobileNum
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
      
      if(json.isLinked){
        setData("Wallet and mobile number are linked.")
      } else {
        setData("Wallet and mobile number are not linked.")
      }
      
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
      
      {data && (
        <Alert  
            severity={data.includes("not") ? "error" : "success"}
            style={{ marginBottom: "20px", width: "100%" }}
        >
        {data}
        </Alert>
)}
      
    </Paper>
  );
};

export default WalletLinking;
