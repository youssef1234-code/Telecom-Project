import React, { useState } from "react";
import { 
  Paper, TextField, Button, Alert, Dialog, DialogActions, 
  DialogContent, DialogContentText, DialogTitle 
} from "@mui/material";
import GenericTable from "./GenericTable";
import StatusCell from "./StatusCell";

const DeleteBenefitsTable = () => {
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ planId: "", mobileNum: "" });
  const [error, setError] = useState("");
  const [dialogOpen, setDialogOpen] = useState(false); // State to control dialog visibility
  const [successMessage, setSuccessMessage] = useState(""); // State to store success message

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/get-benefits`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          mobileNum: filters.mobileNum,
          planId: filters.planId,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        setData("");
        setSuccessMessage("");
        setError(errorData.message || "An error occurred. Please try again.");
        return;
      }
      setSuccessMessage("");
      setError("");
      const json = await response.json();
      setData(json);
    } catch (error) {
      setData("");
      setError("An error occurred while fetching data. Please try again.");
    }
  };

  const handleDelete = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/admin/delete-benefits`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          mobileNum: filters.mobileNum,
          planId: filters.planId,
        }),
      });
      setDialogOpen(false);
      if (!response.ok) {
        const errorData = await response.json();
        setData("");
        setError(errorData.message || "Failed to delete benefits.");
        return;
      }
      //else setMessage that is recieved from the front-end as successMessage
      setError("");
      const successData = await response.json();
      setSuccessMessage(successData.successMessage);
      setData([]); // Clear table data after successful deletion
    } catch (error) {
      setError("An error occurred while deleting benefits. Please try again.");
    }
  };

  const columns = [
    { field: "benefitID", headerName: "Benefit ID", flex: 1 },
    {
      field: "status",
      headerName: "Status",
      flex: 1,
      renderCell: (params) => <StatusCell value={params.value} />,
    },
    { field: "description", headerName: "Description", flex: 2 },
    { field: "validityDate", headerName: "Validity Thru", flex: 2 },
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
      <div style={{ display: "flex", gap: "10px", marginBottom: 20 }}>
        <TextField
          label="Mobile Number"
          variant="outlined"
          value={filters.mobileNum}
          onChange={(e) => handleFilterChange("mobileNum", e.target.value)}
          fullWidth
          InputLabelProps={{
            shrink: true,
          }}
        />
        <TextField
          label="Plan ID"
          variant="outlined"
          value={filters.planId}
          onChange={(e) => handleFilterChange("planId", e.target.value)}
          fullWidth
        />
        <Button
            variant="contained"
            color="primary"
            onClick={handleSubmit}
            sx={{ width: "400px" }} // Set the width for "Fetch Data"
        >
            Fetch Data
        </Button>
        <Button
            variant="contained"
            sx={{ backgroundColor: "#c62828", color: "#fff", width: "400px" }} // Set the width for "Delete Benefits"
            onClick={() => setDialogOpen(true)} // Open the dialog
        >
            Delete Benefits
        </Button>

      </div>

      {error && (
        <Alert severity="error" style={{ marginBottom: "20px" }}>
          {error}
        </Alert>
      )}
        {successMessage && (
        <Alert severity="success" style={{ marginBottom: "20px" }}>
            {successMessage}
        </Alert>
        )}

      <GenericTable data={data} columns={columns} rowIdField="benefitID" />

      {/* Confirmation Dialog */}
      <Dialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        aria-labelledby="confirmation-dialog-title"
        aria-describedby="confirmation-dialog-description"
      >
        <DialogTitle id="confirmation-dialog-title">
          Confirm Deletion
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="confirmation-dialog-description">
            Are you sure you want to delete all benefits? This action cannot be
            undone.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleDelete} sx={{ backgroundColor: "#c62828", color: '#fff' }} >
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
};

export default DeleteBenefitsTable;
