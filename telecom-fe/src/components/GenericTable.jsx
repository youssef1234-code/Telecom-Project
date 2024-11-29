import React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box } from "@mui/material";

const GenericTable = ({ data, columns, rowIdField }) => {
  return (
    <Box sx={{ height: "100%", width: "100%" }}>
      <DataGrid
        rows={data}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5,10,25,50,100]}
        getRowId={(row) => row[rowIdField]} // Use the specified id field
        sx={{ backgroundColor: "#fff" }}
      />
    </Box>
  );
};

export default GenericTable;
