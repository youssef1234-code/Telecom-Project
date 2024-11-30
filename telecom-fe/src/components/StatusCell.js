import React from "react";
import { Box } from "@mui/material";

const StatusCell = ({ value }) => {
    const styles = {
        active: {
          color: "#00BA34", // Text color
          backgroundColor: "rgba(0, 186, 52, 0.2)", // 20% opacity of #00BA34
          border: "2px solid #00BA34", // Border for the active status
        },
        onhold: {
          color: "#FF9F2D", // Text color
          backgroundColor: "rgba(255, 159, 45, 0.2)", // 20% opacity of #FF9F2D
          border: "2px solid #FF9F2D", // Border for the active status
        },
        Resolved: {
            color: "#00BA34", // Text color
            backgroundColor: "rgba(0, 186, 52, 0.2)", // 20% opacity of #00BA34
            border: "2px solid #00BA34", // Border for the active status
        },expired:{
            color:"#E92C2C",
            backgroundColor: "rgba(233, 44, 44, 0.2)",
            border: "2px solid #E92C2C", // Border for the active status
        }
  
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
