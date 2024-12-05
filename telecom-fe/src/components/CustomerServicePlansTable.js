import React, { useState, useEffect } from "react";
import { Paper} from "@mui/material";
import GenericTable from "./GenericTable";

const CustomerServicePlansTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/customer/servicePlans`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);



  const columns = [
    { field: "planID", headerName: "Plan ID",type: "number", flex: 1 },
    { field: "name", headerName: "Plan Name", flex: 2 },
    { field: "description", headerName: "Description", flex: 3 },
    { field: "price", headerName: "Price ($)", type: "number", flex: 1 },
    { field: "smsOffered", headerName: "SMS Offered", type: "number", flex: 1 },
    { field: "minutesOffered", headerName: "Minutes Offered", type: "number", flex: 1 },
    {
      field: "dataOffered",
      headerName: "Data Offered (MB)",
      type: "number",
      flex: 1,
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

      {/* Generic Table */}
      <GenericTable data={data} columns={columns} rowIdField="planID" />
    </Paper>
  );
};

export default CustomerServicePlansTable;
