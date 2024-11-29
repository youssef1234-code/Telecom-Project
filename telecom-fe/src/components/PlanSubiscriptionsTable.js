import React, { useState } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl, Button, Alert } from "@mui/material";
import GenericTable from "./GenericTable";

const PlanSubscriptionsTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ planId: "", startDate: "" });
  const [error, setError] = useState("");  // State to store error message

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    // Send the POST request with planId and startDate
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/subscribed-plan`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          startDate: filters.startDate,
          planId: filters.planId,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.log("Error Data: "+errorData)
        setError(errorData.message || "An error occurred. Please try again.");  // Handle the error message from the backend
        return;
      }

      // Clear any previous error if request is successful
      setError("");
      
      const json = await response.json();
      setData(json);  // Set the data to be displayed in the table
    } catch (error) {
      setError("An error occurred while fetching data. Please try again.");
    }
  };

  const columns = [
    { field: "planId", headerName: "Plan ID", flex: 1 },
    { field: "name", headerName: "Plan Name", flex: 2 },
    { field: "mobileNo", headerName: "Mobile No", flex: 2 },
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
          label="Subiscription Date"
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
          label="Plan ID"
          variant="outlined"
          value={filters.planId}
          onChange={(e) => handleFilterChange("planId", e.target.value)}
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
      <GenericTable data={data} columns={columns} rowIdField="planId" />
    </Paper>
  );
};

export default PlanSubscriptionsTable;
