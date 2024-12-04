import React, { useState } from "react";
import { Paper, TextField, Button, Alert } from "@mui/material";
import GenericTable from "./GenericTable";

const MonthUsageTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ mobileNum: "" });
  const [error, setError] = useState("");

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    // Send the POST request with planId and startDate
    try {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/monthUsage`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            mobileNum: localStorage.getItem("mobileNumber"),
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setData("");
        setError(errorData.message || "An error occurred. Please try again."); // Handle the error message from the backend
        return;
      }

      // Clear any previous error if request is successful
      setError("");

      const json = await response.json();
      setData(json); // Set the data to be displayed in the table
    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };

  const columns = [
    {
      field: "totalData",
      headerName: "Total Data Used",
      flex: 1,
    },
    {
      field: "totalMins",
      headerName: "Minutes Used",
      type: "number",
      flex: 2,
    },
    {
      field: "totalSMS",
      headerName: "SMS Used",
      type: "number",
      flex: 2,
    },
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
          label="Mobile Number"
          variant="outlined"
          value={localStorage.getItem("mobileNumber")}
          onChange={(e) => handleFilterChange("startDate", e.target.value)}
          fullWidth
          InputLabelProps={{
            shrink: true,
          }}
          disabled={true}
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

      {/* Generic Table */}
      <GenericTable data={data} columns={columns} rowIdField="totalData" />
    </Paper>
  );
};

export default MonthUsageTable;
