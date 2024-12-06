import React, { useState, useContext, useEffect } from "react";
import { Menu } from "antd";
import { Route, Routes, useLocation } from "react-router-dom";
import { AuthContext } from "../App";
import { Box, Typography } from "@mui/material";
import {
  ExitToApp,
  AccountCircle,
  Store,
  Receipt,
  Loyalty,
  Sms,
  Payment,
  Wallet,
  PeopleAlt,
  Subscriptions,
  DataUsage,
  LocalGroceryStore,
  Paid,
} from "@mui/icons-material";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";

const { SubMenu, Item } = Menu;

const menuItems = [
  { title: "Customer Accounts", icon: <AccountCircle style={{ fontSize: "24px" }} />, path: "/admin/customers" },
  { title: "Customer Profiles", icon: <PeopleAlt style={{ fontSize: "24px" }} />, path: "/admin/profiles" },
  { title: "Resolved Tickets", icon: <Receipt style={{ fontSize: "24px" }} />, path: "/admin/tickets" },
  { title: "Subscribed Plan Search", icon: <Subscriptions style={{ fontSize: "24px" }} />, path: "/admin/subscribed-plan" },
  { title: "Account Usage", icon: <DataUsage style={{ fontSize: "24px" }} />, path: "/admin/account-usage" },
  { title: "Remove Benefits", icon: <Loyalty style={{ fontSize: "24px" }} />, path: "/admin/remove-benefits" },
  { title: "SMS Offers", icon: <Sms style={{ fontSize: "24px" }} />, path: "/admin/sms-offers" },
  { title: "Wallets Details", icon: <Wallet style={{ fontSize: "24px" }} />, path: "/admin/wallets" },
  { title: "Payments", icon: <Payment style={{ fontSize: "24px" }} />, path: "/admin/payments" },
  { title: "Cashback Amount", icon: <Wallet style={{ fontSize: "24px" }} />, path: "/admin/cashback-amount" },
  { title: "Wallet Linking", icon: <Wallet style={{ fontSize: "24px" }} />, path: "/admin/wallet-linking" },
  { title: "Update Points", icon: <Loyalty style={{ fontSize: "24px" }} />, path: "/admin/update-points" },
];

const SidebarLayout = ({ children }) => {
  // Retrieve the sidebar state from localStorage or default to true
  const savedSidebarState = localStorage.getItem("sidebarState");
  const [isSidebarOpen, setSidebarOpen] = useState(savedSidebarState === "true");

  const location = useLocation();
  const { signOut } = useContext(AuthContext);

  // Save the sidebar state to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem("sidebarState", isSidebarOpen.toString());
  }, [isSidebarOpen]);

  const handleSignOut = () => {
    signOut();
    window.location.href = "/";
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
          height: "100vh",
        }}
      >
        <Menu
          mode="inline"
          defaultSelectedKeys={[location.pathname]}
          style={{
            height: "100%",
            paddingTop: "20px",
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
                  Admin Portal
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

            {/* Submenus */}
            <SubMenu
              key="stores"
              icon={<LocalGroceryStore />}
              title={isSidebarOpen ? "Stores" : null}
              style={{ marginBottom: "10px" }}
            >
              <Item key="/admin/stores" style={{ height: "50px" }}>
                <a href="/admin/stores">Physical Stores</a>
              </Item>
              <Item key="/admin/eshops" style={{ height: "50px" }}>
                <a href="/admin/eshops">E-Shops</a>
              </Item>
            </SubMenu>

            <SubMenu
              key="transactions"
              icon={<Paid />}
              title={isSidebarOpen ? "Transactions" : null}
              style={{ marginBottom: "10px" }}
            >
              <Item key="/admin/cashback-transactions" style={{ height: "50px" }}>
                <a href="/admin/cashback-transactions">Cashback Transactions</a>
              </Item>
              <Item key="/admin/accepted-transactions" style={{ height: "50px" }}>
                <a href="/admin/accepted-transactions">Accepted Transactions</a>
              </Item>
              <Item key="/admin/transaction-average" style={{ height: "50px" }}>
                <a href="/admin/transaction-average">Transaction Average</a>
              </Item>
            </SubMenu>
          </Menu.ItemGroup>

          {/* Main Menu Items */}
          {menuItems.map((item) => (
            <Item
              key={item.path}
              icon={item.icon}
              style={{
                height: "50px",
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
          {menuItems.map((item, index) => (
            <Route
              key={index}
              path={item.path}
              element={<Typography variant="h4">{item.title}</Typography>}
            />
          ))}
        </Routes>
        {children}
      </Box>
    </Box>
  );
};

export default SidebarLayout;