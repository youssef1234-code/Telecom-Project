import React, { useState, useEffect } from "react";
import {
  Paper,
  TextField,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
} from "@mui/material";
import GenericTable from "./GenericTable";
import StatusCell from "./StatusCell";

const AllBenefitsTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/all-benefits`
      );
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };
  /* 
  const filteredData = data.filter((item) => {
    return (
      (!filters.ticketId || item.ticketId === filters.ticketId) &&
      (!filters.mobileNo || item.mobileNo.includes(filters.mobileNo)) &&
      (!filters.search ||
        item.issueDescription
          .toLowerCase()
          .includes(filters.search.toLowerCase()))
    );
  }); */

  const columns = [
    { field: "benefitID", headerName: "Benefit ID", flex: 1 },
    { field: "description", headerName: "Description", flex: 1 },
    { field: "validityDate", headerName: "Validity Date", flex: 2 },
    { field: "status", headerName: "Status", flex: 2 },
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
      {/* <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
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
      </div> */}

      {/* Generic Table */}
      <GenericTable data={data} columns={columns} rowIdField="benefitId" />
    </Paper>
  );
};

export default AllBenefitsTable;
