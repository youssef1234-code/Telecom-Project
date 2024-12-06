import React, { useState } from "react";
import {
  Paper,
  TextField,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Button,
  Alert,
  Typography,
  Box,
} from "@mui/material";
import GenericTable from "./GenericTable";

const CashbackAmount = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({
    mobileNum: "",
    benefitID: "",
    planID: "",
  });
  const [error, setError] = useState(""); // State to store error message

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch(
        `${process.env.REACT_APP_SERVER_URL}/api/customer/get-cashback-amount`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            mobileNum: filters.mobileNum,
            benefitID: filters.benefitID,
            planID: filters.planID,
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
      setData(json);

      if (json.success) {
        setData("Updated Succesfully.");
      } else {
        setData("Failed to Update.");
      }
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
      <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
        <TextField
          label="Mobile Number"
          variant="outlined"
          value={filters.mobileNum}
          onChange={(e) => handleFilterChange("mobileNum", e.target.value)}
          fullWidth
        />
        <TextField
          label="BenefitId"
          variant="outlined"
          value={filters.benefitID}
          onChange={(e) => handleFilterChange("benefitID", e.target.value)}
          fullWidth
        />
        <TextField
          label="Plan ID"
          variant="outlined"
          value={filters.planID}
          onChange={(e) => handleFilterChange("planID", e.target.value)}
          fullWidth
        />

        <Button variant="contained" color="primary" onClick={handleSubmit}>
          Update
        </Button>
      </div>

      {/* Show error alert if any */}
      {error && (
        <Alert severity="error" style={{ marginBottom: "20px" }}>
          {error}
        </Alert>
      )}

      {data && (
        <Alert
          severity={data.includes("not") ? "error" : "success"}
          style={{ marginBottom: "20px", width: "100%" }}
        >
          {data}
        </Alert>
      )}
    </Paper>
  );
};

export default CashbackAmount;
