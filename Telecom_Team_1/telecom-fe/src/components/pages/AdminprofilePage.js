import React from "react";
import { Box, Typography } from "@mui/material";
import SidebarLayout from "../SidebarLayout";
import CustomerProfilesTable from "../CustomerProfilesTable";

const AdminCustomersPage = () => {
  return (
    <SidebarLayout>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          height: "100vh", // Make it occupy the full viewport height
        }}
      >
        {/* Page Header */}
        <Typography
          variant="h4"
          component="h1"
          sx={{
            padding: "16px 24px",
            backgroundColor: "#f5f5f5",
            borderBottom: "1px solid #ddd",
            fontWeight: "bold",
            color: "#333",
          }}
        >
          Customer Profiles
        </Typography>

        {/* Main Content Area */}
        <Box
          sx={{
            flex: 1, // Fills available vertical space
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "#fafafa",
            padding: 2,
          }}
        >
          {/* Table Container */}
          <Box
            sx={{
              width: "100%",
              height: "100%", // Ensures table fills vertical space
              padding: 2,
              background: "#fff",
              boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
              borderRadius: 2,
            }}
          >
            <div style={{ flexGrow: 1, display: "flex", flexDirection: "column" }}>
  <CustomerProfilesTable />
</div>
          </Box>
        </Box>
      </Box>
    </SidebarLayout>
  );
};

export default AdminCustomersPage;
