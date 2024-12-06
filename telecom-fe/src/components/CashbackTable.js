import React, { useState } from "react";
import { Paper, TextField, Button, Alert, Box } from "@mui/material";
import GenericTable from "./GenericTable"; // Assuming GenericTable is a reusable table component

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
            mobileNum: localStorage.getItem("mobileNumber"),
            nId: filters.nationalId,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setData([]);
        setError(errorData.message || "An error occurred. Please try again.");
        return;
      }

      setError("");
      const json = await response.json();
      setData(json); // Set the data to be displayed in the table
    } catch (error) {
      setData([]);
      setError("An error occurred while fetching data. Please try again.");
    }
  };

  const columns = [
    { field: "benefitID", headerName: "Benefit ID", flex: 1 },
    { field: "amount", headerName: "Amount", type: "number", flex: 1 },
    { field: "walletID", headerName: "Wallet ID", flex: 1 },
    { field: "cashbackID", headerName: "Cashback ID", flex: 1 },
    { field: "creditDate", headerName: "Credit Date", flex: 1 },
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

      {/* Show data in table format */}
      <Box
        sx={{
          flex: 1, // Fills available vertical space
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          backgroundColor: "#fafafa",
          padding: 2,
        }}
      >
      </Box>
        {/* Generic Table */}
        <GenericTable data={data} columns={columns} rowIdField="cashbackID" />
    </Paper>
  );
};

export default CashbackTable;