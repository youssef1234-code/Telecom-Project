import React, { useState, useEffect } from "react";
import { Paper, TextField, MenuItem, Select, InputLabel, FormControl } from "@mui/material";
import GenericTable from "./GenericTable";

const StoreVouchersTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ voucherId: "", shopId: "", search: "" });

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/store-vouchers`);
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
      (!filters.voucherId || item.voucherId === filters.voucherId) &&
      (!filters.shopId || item.shopId === filters.shopId) &&
      (!filters.search || item.address.toLowerCase().includes(filters.search.toLowerCase()))
    );
  });

  const columns = [
    { field: "shopId", headerName: "Shop ID", flex: 1 },
    { field: "address", headerName: "Address", flex: 2 },
    { field: "workingHours", headerName: "Working Hours", flex: 1 },
    { field: "voucherId", headerName: "Voucher ID", flex: 1 },
    { field: "value", headerName: "Voucher Value", type: "number", flex: 1 },
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
          label="Search by Address"
          variant="outlined"
          onChange={(e) => handleFilterChange("search", e.target.value)}
          fullWidth
        />
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Voucher ID</InputLabel>
          <Select
            value={filters.voucherId}
            onChange={(e) => handleFilterChange("voucherId", e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            {data.map((item) => (
              <MenuItem key={item.voucherId} value={item.voucherId}>
                {item.voucherId}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Shop ID</InputLabel>
          <Select
            value={filters.shopId}
            onChange={(e) => handleFilterChange("shopId", e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            {data.map((item) => (
              <MenuItem key={item.shopId} value={item.shopId}>
                {item.shopId}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </div>

      {/* Generic Table */}
      <GenericTable data={filteredData} columns={columns} rowIdField="voucherId" />
    </Paper>
  );
};

export default StoreVouchersTable;
