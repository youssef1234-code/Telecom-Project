import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import GenericTable from "./GenericTable";

const EShopTable = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/e-shops`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);


  const columns = [
    { field: "shopID", headerName: "Shop ID", type: "number", flex: 1 },
    { field: "url", headerName: "URL", flex: 2 },
    { field: "rating", headerName: "Rating", flex: 1 },
    { field: "voucherID", headerName: "Voucher ID", flex: 1 },
    { field: "value", headerName: "Value", flex: 1 },
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

export default EShopTable;