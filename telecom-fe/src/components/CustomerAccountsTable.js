import React, { useState, useEffect } from 'react';
import {
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TableSortLabel, Paper,
  TextField, MenuItem, Select, InputLabel, FormControl, Button, Pagination
} from '@mui/material';

const CustomerAccountsTable = () => {
  const [data, setData] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [sortConfig, setSortConfig] = useState({ key: '', direction: 'asc' });
  const [filters, setFilters] = useState({ status: '', accountType: '', planName: '', search: '' });
  const [page, setPage] = useState(1);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/customer-accounts`);
      const json = await response.json();
      setData(json);
      setFilteredData(json);
    };
    fetchData();
  }, []);

  useEffect(() => {
    let tempData = [...data];

    // Apply filters
    if (filters.status) tempData = tempData.filter(item => item.status === filters.status);
    if (filters.accountType) tempData = tempData.filter(item => item.accountType === filters.accountType);
    if (filters.planName) tempData = tempData.filter(item => item.planName.includes(filters.planName));
    if (filters.search) tempData = tempData.filter(item => item.mobileNo.includes(filters.search));

    // Apply sorting
    if (sortConfig.key) {
      tempData.sort((a, b) => {
        const aValue = a[sortConfig.key];
        const bValue = b[sortConfig.key];
        if (aValue < bValue) return sortConfig.direction === 'asc' ? -1 : 1;
        if (aValue > bValue) return sortConfig.direction === 'asc' ? 1 : -1;
        return 0;
      });
    }

    setFilteredData(tempData);
  }, [filters, sortConfig, data]);

  const handleSort = (key) => {
    setSortConfig(prev => ({
      key,
      direction: prev.key === key && prev.direction === 'asc' ? 'desc' : 'asc'
    }));
  };

  const handleFilterChange = (key, value) => {
    setFilters(prev => ({ ...prev, [key]: value }));
  };

  const handleSearchChange = (e) => {
    setFilters(prev => ({ ...prev, search: e.target.value }));
  };

  const handleRowsPerPageChange = (e) => {
    setRowsPerPage(Number(e.target.value));
    setPage(1);
  };

  const paginatedData = filteredData.slice((page - 1) * rowsPerPage, page * rowsPerPage);

  return (
    <Paper style={{ padding: 20 }}>
      <div style={{ display: 'flex', gap: '10px', marginBottom: 20 }}>
        <TextField
          label="Search by Mobile No"
          variant="outlined"
          onChange={handleSearchChange}
          fullWidth
        />
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Status</InputLabel>
          <Select value={filters.status} onChange={(e) => handleFilterChange('status', e.target.value)}>
            <MenuItem value="">All</MenuItem>
            <MenuItem value="active">Active</MenuItem>
            <MenuItem value="inactive">Inactive</MenuItem>
          </Select>
        </FormControl>
        <FormControl variant="outlined" fullWidth>
          <InputLabel>Account Type</InputLabel>
          <Select value={filters.accountType} onChange={(e) => handleFilterChange('accountType', e.target.value)}>
            <MenuItem value="">All</MenuItem>
            <MenuItem value="postpaid">Postpaid</MenuItem>
            <MenuItem value="prepaid">Prepaid</MenuItem>
          </Select>
        </FormControl>
        <TextField
          label="Plan Name"
          variant="outlined"
          onChange={(e) => handleFilterChange('planName', e.target.value)}
          fullWidth
        />
      </div>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              {['description', 'price', 'minutesOffered', 'dataOffered', 'status', 'mobileNo', 'planName', 'balance'].map(column => (
                <TableCell key={column}>
                  <TableSortLabel
                    active={sortConfig.key === column}
                    direction={sortConfig.key === column ? sortConfig.direction : 'asc'}
                    onClick={() => handleSort(column)}
                  >
                    {column}
                  </TableSortLabel>
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {paginatedData.map((row, index) => (
              <TableRow key={index}>
                <TableCell>{row.description}</TableCell>
                <TableCell>{row.price}</TableCell>
                <TableCell>{row.minutesOffered}</TableCell>
                <TableCell>{row.dataOffered}</TableCell>
                <TableCell>{row.status}</TableCell>
                <TableCell>{row.mobileNo}</TableCell>
                <TableCell>{row.planName}</TableCell>
                <TableCell>{row.balance}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: 20 }}>
        <FormControl variant="outlined" style={{ width: 100 }}>
          <InputLabel>Rows</InputLabel>
          <Select value={rowsPerPage} onChange={handleRowsPerPageChange}>
            {[5, 10, 20].map(size => <MenuItem key={size} value={size}>{size}</MenuItem>)}
          </Select>
        </FormControl>
        <Pagination
          count={Math.ceil(filteredData.length / rowsPerPage)}
          page={page}
          onChange={(e, value) => setPage(value)}
        />
      </div>
    </Paper>
  );
};

export default CustomerAccountsTable;
