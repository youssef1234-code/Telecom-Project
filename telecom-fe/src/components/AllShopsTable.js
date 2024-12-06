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

const AllShopsTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/all-shops`
      );
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

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
    { field: "shopID", headerName: "Shop ID", flex: 1 },
    { field: "name", headerName: "Name", flex: 1 },
    { field: "category", headerName: "Category", flex: 2 },
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
      {/* Generic Table */}
      <GenericTable data={data} columns={columns} rowIdField="shopID" />
    </Paper>
  );
};

export default AllShopsTable;
