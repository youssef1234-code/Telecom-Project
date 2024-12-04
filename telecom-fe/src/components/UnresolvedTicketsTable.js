import React, { useState } from "react";
import { Paper, TextField, Button, Alert } from "@mui/material";
import GenericTable from "./GenericTable";

const UnresolvedTicketsTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({
    mobileNum: "",
    nId: "",
    planName: "",
  });
  const [error, setError] = useState("");

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    // Send the POST request with planId and startDate
    try {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/unresolved`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            mobileNum: localStorage.getItem("mobileNumber"),
            nId: filters.nId,
            planName: filters.planName,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setData("");
        setError(errorData.message || "An error occurred. Please try again."); // Handle the error message from the backend
        return;
      }

      // Clear any previous error if request is successful
      setError("");

      const json = await response.json();
      setData(json); // Set the data to be displayed in the table
    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };

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
      <h1> {data} </h1>
    </Paper>
  );
};

export default UnresolvedTicketsTable;
