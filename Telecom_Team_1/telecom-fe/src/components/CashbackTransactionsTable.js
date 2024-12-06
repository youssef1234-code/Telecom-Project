import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import GenericTable from "./GenericTable";

const CashbackTransactionsTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/cashback-transactions`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);


  const columns = [
    { field: "walletID", headerName: "Wallet ID", flex: 1 },
    { field: "trxCount", headerName: "Number Of Transactions", flex: 1 },
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

export default CashbackTransactionsTable;