import React, { useState, useEffect } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl } from "@mui/material";
import GenericTable from "./GenericTable";
import StatusCell from "./StatusCell";

const CustomerProfilesTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ status: "", accountType: "", planName: "", search: "" });

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/customer-profiles`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const filteredData = data.filter((item) => {
    return (
      (!filters.status || item.status === filters.status) &&
      (!filters.accountType || item.accountType === filters.accountType) &&
      (!filters.search || item.mobileNo.includes(filters.search))
    );
  });

  const columns = [
    // Personal Information
    { field: "nationalID", headerName: "National ID", flex: 1 },
    { field: "firstName", headerName: "First Name", flex: 1 },
    { field: "lastName", headerName: "Last Name", flex: 1 },
    { field: "email", headerName: "Email", flex: 2 },
    { field: "address", headerName: "Address", flex: 2 },
    { field: "dateOfBirth", headerName: "Date of Birth", flex: 1 },
  
    // Customer Account Fields
    { field: "mobileNo", headerName: "Mobile No", flex: 1 },
    { field: "accountType", headerName: "Account Type", flex: 1 },
    {
      field: "status",
      headerName: "Status",
      flex: 1,
      renderCell: (params) => <StatusCell value={params.value} />,
    },
    { field: "startDate", headerName: "Start Date", flex: 1 },
    { field: "balance", headerName: "Balance", type: "number", flex: 1 },
    { field: "points", headerName: "Points", type: "number", flex: 1 },
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
          label="Search by Mobile No"
          variant="outlined"
          onChange={(e) => handleFilterChange("search", e.target.value)}
          fullWidth
        />
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Status</InputLabel>
          <Select
            value={filters.status}
            onChange={(e) => handleFilterChange("status", e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            <MenuItem value="active">Active</MenuItem>
            <MenuItem value="inactive">Inactive</MenuItem>
          </Select>
        </FormControl>
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Account Type</InputLabel>
          <Select
            value={filters.accountType}
            onChange={(e) => handleFilterChange("accountType", e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            <MenuItem value="postpaid">Postpaid</MenuItem>
            <MenuItem value="prepaid">Prepaid</MenuItem>
          </Select>
        </FormControl>
      </div>

      {/* Generic Table */}
      <GenericTable data={filteredData} columns={columns} rowIdField="mobileNo" />
    </Paper>
  );
};

export default CustomerProfilesTable;
