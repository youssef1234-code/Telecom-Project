import React, { useState } from "react";
import { Paper, TextField,Button, Alert } from "@mui/material";
import GenericTable from "./GenericTable";

const ConsumptionTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ planName: "", startDate: "",endDate: "" });
  const [error, setError] = useState("");

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    // Send the POST request with planId and startDate
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/customer/consumption`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          startDate: filters.startDate,
          planName: filters.planName,
          endDate: filters.endDate
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
      setData(json);  // Set the data to be displayed in the table
    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };


  const columns = [
    { field: "minutesUsed", headerName: "Minutes Used",type:"number", flex: 1 },
    { field: "dataConsumption", headerName: "Data Consumption(MB)",type:"number", flex: 2 },
    { field: "sms", headerName: "SMS Sent", type:"number",flex: 2 },
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
      <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
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

        <TextField
          label="Plan Name"
          variant="outlined"
          value={filters.planName}
          onChange={(e) => handleFilterChange("planName", e.target.value)}
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

      {/* Generic Table */}
      <GenericTable data={data} columns={columns} rowIdField="sms" />
    </Paper>
  );
};

export default ConsumptionTable;
