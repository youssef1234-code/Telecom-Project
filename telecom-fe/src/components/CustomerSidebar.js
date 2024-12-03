import React, { useState, useContext } from "react";
import { Menu } from "antd";
import { Route, Routes, useLocation } from "react-router-dom";
import { AuthContext } from "../App";
import { Box, Typography } from "@mui/material";
import {
  ExitToApp,
  ListAlt,
  QueryStats,
  Sms,
  Loyalty,
  Receipt,
  Redeem,
  Payment,
  MoneyOffCsred,
  Store,
  Cached,
} from "@mui/icons-material";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";

const { SubMenu, Item } = Menu;

// Menu Items for Customer Pages
const customerMenuItems = [
  { title: "Service Plans", icon: <ListAlt style={{ fontSize: "24px" }} />, path: "/customer/service-plans" },
  { title: "Usage Consumption", icon: <QueryStats style={{ fontSize: "24px" }} />, path: "/customer/usage-consumption" },
  { title: "Unsubscribed Plans", icon: <Sms style={{ fontSize: "24px" }} />, path: "/customer/unsubscribed-plans" },
  { title: "Active Plans Usage", icon: <QueryStats style={{ fontSize: "24px" }} />, path: "/customer/active-plans-usage" },
  { title: "Cashback Transactions", icon: <Loyalty style={{ fontSize: "24px" }} />, path: "/customer/cashback-transactions" },
  { title: "Active Benefits", icon: <Loyalty style={{ fontSize: "24px" }} />, path: "/customer/active-benefits" },
  { title: "Unresolved Tickets", icon: <Receipt style={{ fontSize: "24px" }} />, path: "/customer/unresolved-tickets" },
  { title: "Highest Value Voucher", icon: <Redeem style={{ fontSize: "24px" }} />, path: "/customer/highest-voucher" },
  { title: "Remaining Payment", icon: <Payment style={{ fontSize: "24px" }} />, path: "/customer/remaining-payment" },
  { title: "Extra Payment", icon: <Payment style={{ fontSize: "24px" }} />, path: "/customer/extra-payment" },
  { title: "Top Payments", icon: <MoneyOffCsred style={{ fontSize: "24px" }} />, path: "/customer/top-payments" },
  { title: "All Shops", icon: <Store style={{ fontSize: "24px" }} />, path: "/customer/all-shops" },
  { title: "Past Subscriptions", icon: <ListAlt style={{ fontSize: "24px" }} />, path: "/customer/past-subscriptions" },
  { title: "Renew Subscription", icon: <Cached style={{ fontSize: "24px" }} />, path: "/customer/renew-subscription" },
  { title: "Cashback Amount", icon: <Loyalty style={{ fontSize: "24px" }} />, path: "/customer/cashback-amount" },
  { title: "Recharge Balance", icon: <Payment style={{ fontSize: "24px" }} />, path: "/customer/recharge-balance" },
  { title: "Redeem Voucher", icon: <Redeem style={{ fontSize: "24px" }} />, path: "/customer/redeem-voucher" },
];

const CustomerSidebar = ({ children }) => {
  const [isSidebarOpen, setSidebarOpen] = useState(true);
  const location = useLocation();
  const { signOut } = useContext(AuthContext);

  const handleSignOut = () => {
    signOut();
    window.location.href = "/customer";
  };

  const toggleSidebar = () => {
    setSidebarOpen(!isSidebarOpen);
  };

  return (
    <Box sx={{ display: "flex" }}>
      {/* Sidebar */}
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          width: isSidebarOpen ? 256 : 80,
          transition: "width 0.3s ease",
          backgroundColor: "#001529",
          color: "#fff",
          height: "80vh",
        }}
      >
        <Menu
          mode="inline"
          defaultSelectedKeys={[location.pathname]}
          style={{
            height: "100%",
            paddingTop: "0px",
          }}
          inlineCollapsed={!isSidebarOpen}
        >
          <Menu.ItemGroup
            title={
              isSidebarOpen && (
                <span
                  style={{
                    fontSize: "20px",
                    fontWeight: "bold",
                    textAlign: "center",
                  }}
                >
                  Customer Portal
                </span>
              )
            }
          >
            {/* Collapse Button */}
            <Item
              key="collapse"
              icon={isSidebarOpen ? <MenuFoldOutlined /> : <MenuUnfoldOutlined />}
              onClick={toggleSidebar}
              style={{
                height: "50px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
              }}
            >
              {isSidebarOpen ? "Collapse Sidebar" : null}
            </Item>
          </Menu.ItemGroup>

          {/* Main Menu Items */}
          {customerMenuItems.map((item) => (
            <Item
              key={item.path}
              icon={item.icon}
              style={{
                height: "40px",
                display: "flex",
                alignItems: "center",
              }}
            >
              <a href={item.path}>{isSidebarOpen ? item.title : null}</a>
            </Item>
          ))}

          {/* Sign Out */}
          <Menu.Item
            key="signout"
            icon={<ExitToApp />}
            style={{
              color: "red",
              height: "50px",
              display: "flex",
              alignItems: "center",
            }}
            onClick={handleSignOut}
          >
            {isSidebarOpen ? "Sign Out" : null}
          </Menu.Item>
        </Menu>
      </Box>

      {/* Main Content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          transition: "margin-left 0.3s ease",
          minHeight: "100vh",
          backgroundColor: "#fafafa",
          color: "#000",
        }}
      >
        <Routes>
          {customerMenuItems.map((item, index) => (
            <Route
              key={index}
              path={item.path}
              element={<Typography variant="h4">{item.title} Page Placeholder</Typography>}
            />
          ))}
        </Routes>
        {children}
      </Box>
    </Box>
  );
};

export default CustomerSidebar;