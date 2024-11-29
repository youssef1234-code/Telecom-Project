import React, { useState, useEffect } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl } from "@mui/material";
import GenericTable from "./GenericTable";

const TicketsTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ ticketId: "", mobileNo: "", search: "" });

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/resolved-tickets`);
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
      (!filters.ticketId || item.ticketId === filters.ticketId) &&
      (!filters.mobileNo || item.mobileNo.includes(filters.mobileNo)) &&
      (!filters.search || item.issueDescription.toLowerCase().includes(filters.search.toLowerCase()))
    );
  });

  const columns = [
    { field: "ticketId", headerName: "Ticket ID", flex: 1 },
    { field: "mobileNo", headerName: "Mobile No", flex: 1 },
    { field: "issueDescription", headerName: "Issue Description", flex: 2 },
    { field: "status", headerName: "Status", flex: 1 },
    { field: "priorityLevel", headerName: "Priority Level", type: "number", flex: 1 },
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
          label="Search by Issue Description"
          variant="outlined"
          onChange={(e) => handleFilterChange("search", e.target.value)}
          fullWidth
        />
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Ticket ID</InputLabel>
          <Select
            value={filters.ticketId}
            onChange={(e) => handleFilterChange("ticketId", e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            {data.map((item) => (
              <MenuItem key={item.ticketId} value={item.ticketId}>
                {item.ticketId}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          label="Search by Mobile No"
          variant="outlined"
          onChange={(e) => handleFilterChange("mobileNo", e.target.value)}
          fullWidth
        />
      </div>

      {/* Generic Table */}
      <GenericTable data={filteredData} columns={columns} rowIdField="ticketId" />
    </Paper>
  );
};

export default TicketsTable;