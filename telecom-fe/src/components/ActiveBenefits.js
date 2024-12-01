import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import GenericTable from "./GenericTable";
import StatusCell from "./StatusCell";

const ActiveBenefits = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/customer/all-benefits`);
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);


  const columns = [
    { field: "benefitID", headerName: "Benefit ID", type: "number", flex: 1 },
    { field: "description", headerName: "Description", flex: 1 },
    { field: "validityDate", headerName: "Valid Thru", flex: 1 },
    {
      field: "status",
      headerName: "Status",
      flex: 1,
      renderCell: (params) => <StatusCell value={params.value} />,
    },   
     //THIS MUST BE REMOVED !!!
    //{ field: "mobileNo", headerName: "Mobile No.", flex: 1 },
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
      <GenericTable data={data} columns={columns} rowIdField="benefitID" />
    </Paper>
  );
};

export default ActiveBenefits;