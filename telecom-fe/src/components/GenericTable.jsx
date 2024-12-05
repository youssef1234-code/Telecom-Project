import React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box } from "@mui/material";

const GenericTable = ({ data, columns, rowIdField }) => {
  // Filter out the sequentialId column to hide it
  const filteredColumns = columns.filter((column) => column.field !== 'SequentialId');

  return (
    <Box>
      <DataGrid
        rows={data}
        columns={filteredColumns}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5, 10, 25, 50, 100]}
        getRowId={(row) => row[rowIdField]} // Use the specified id field
        sx={{ backgroundColor: "#fff" }}
      />
    </Box>
  );
};

export default GenericTable;
