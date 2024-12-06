import React, { useState } from "react";
import {
  Paper,
  TextField,
  Button,
  Box,
  Alert,
  Typography,
} from "@mui/material";

const CashbackAmount = () => {
  const [data, setData] = useState([]);
  const [cashback, setCashback] = useState(null); // State to store cashback amount
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
        `${process.env.REACT_APP_SERVER_URL}/api/customer/update-cashback`,
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
        setError(errorData.message || "An error occurred. Please try again.");
        return;
      }

      setError(""); // Clear error if request is successful
      const json = await response.json();
      setData(json.message);

    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };

  const handleGetCashback = async () => {
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
            planID: filters.planID,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setCashback(null); // Reset cashback if there's an error
        setError(errorData.message || "Failed to fetch cashback amount.");
        return;
      }

      setError(""); // Clear error if request is successful
      const json = await response.json();
      setCashback(json || 0); // Handle null response by defaulting to 0

    } catch (error) {
      setCashback(null);
      setError("An error occurred while fetching cashback amount.");
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
        <Button variant="contained" color="secondary" onClick={handleGetCashback}>
          Get
        </Button>
      </div>

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

      <Box
      sx={{
        flex: 1, // Fills available vertical space
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fafafa",
        padding: 2,
      }}>
        <Typography variant = "h4" gutterBottom>
        Cashback Amount: {cashback !== null ? cashback : "No Cashback Found"}
        </Typography>
      </Box>
    </Paper>
  );
};

export default CashbackAmount;