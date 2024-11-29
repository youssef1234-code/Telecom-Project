import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import GenericTable from "./GenericTable";

const AccountPayments = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/payments`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);


  const columns = [
    { field: "paymentID", headerName: "Payment ID", flex: 1 },
    { field: "mobileNo", headerName: "Mobile No.", flex: 1 },
    { field: "amount", headerName: "Amount", flex: 1 },
    { field: "paymentMethod", headerName: "Payment Method", flex: 1 },
    { field: "status", headerName: "Status", flex: 1 },
    { field: "dateOfPayment", headerName: "Date of Payment", flex: 1 },
  
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
      <GenericTable data={data} columns={columns} rowIdField="paymentID" />
    </Paper>
  );
};

export default AccountPayments;