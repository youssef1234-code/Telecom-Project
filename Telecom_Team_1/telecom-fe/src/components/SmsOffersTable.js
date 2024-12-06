import React, { useState } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl, Button, Alert } from "@mui/material";
import GenericTable from "./GenericTable";

const SmsOffersTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ mobileNum: ""});
  const [error, setError] = useState("");  // State to store error message

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/sms-offers`, {
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
      setData(json);
    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };


  const columns = [
    { field: "benefitID", headerName: "Benefit ID", flex: 1 },
    { field: "offerID", headerName: "Offer ID", flex: 1 },
    { field: "internetOffered", headerName: "Offered Internet", flex: 1 },
    { field: "smsOffered", headerName: "Offered SMS", flex: 1 },
    { field: "minutesOffered", headerName: "Offered Minutes", flex: 1 },
  ];

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
    <div style={{ display: "flex", gap: "10px", marginBottom: 20, alignItems: "center" }}>
    <TextField
        label="Mobile Number"
        variant="outlined"
        value={filters.mobileNum}
        onChange={(e) => handleFilterChange("mobileNum", e.target.value)}
        style={{ flexGrow: 1, maxWidth: "300px" }} // Limit width of TextField
    />
    <Button
        variant="contained"
        color="primary"
        onClick={handleSubmit}
        style={{ padding: "10px 20px" }} // Adjust button size
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
      <GenericTable data={data} columns={columns} rowIdField="benefitID" />
    </Paper>
  );
};

export default SmsOffersTable;
