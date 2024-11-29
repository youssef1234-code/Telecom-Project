import React from "react";
import { Box } from "@mui/material";

const StatusCell = ({ value }) => {
    const styles = {
        active: {
          color: "#00BA34", // Text color
          backgroundColor: "rgba(0, 186, 52, 0.2)", // 20% opacity of #00BA34
        },
        onhold: {
          color: "#FF9F2D", // Text color
          backgroundColor: "rgba(255, 159, 45, 0.2)", // 20% opacity of #FF9F2D
        },
        Resolved: {
            color: "#00BA34", // Text color
            backgroundColor: "rgba(0, 186, 52, 0.2)", // 20% opacity of #00BA34
        },
  
    };

  return (
    <Box
      sx={{
        ...styles[value],
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: "0 12px", // Space inside the badge
        margin: "10px 0px 0px 0px", // Space outside the badge
        height: "30px",
        borderRadius: "20px",
        textAlign: "center",
        fontSize: "0.9rem",
        fontWeight: "light",
        minWidth: "80px",
          }}
    >
      {value}
    </Box>
  );
};

export default StatusCell;
