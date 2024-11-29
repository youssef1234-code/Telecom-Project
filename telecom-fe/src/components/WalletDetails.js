import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import GenericTable from "./GenericTable";

const WalletDetails = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/wallet-details`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);


  const columns = [
    { field: "walletID", headerName: "Wallet ID", type: "number", flex: 1 },
    { field: "nationalID", headerName: "National ID", flex: 1 },
    { field: "firstName", headerName: "First Name", flex: 1 },
    { field: "lastName", headerName: "Last Name", flex: 1 },
    { field: "mobileNo", headerName: "Mobile No.", flex: 1 },
    { field: "currentBalance", headerName: "Balance", flex: 2 },
    { field: "currency", headerName: "Currency", flex: 1 },
    { field: "lastModifiedDate", headerName: "Last Modified Date", flex: 1 },
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
      <GenericTable data={data} columns={columns} rowIdField="walletID" />
    </Paper>
  );
};

export default WalletDetails;