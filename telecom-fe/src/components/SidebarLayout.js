import React, { useState,useContext } from "react";
import { Sidebar } from "flowbite-react";
import { Route, Routes, useLocation } from "react-router-dom";
import { AuthContext } from "../App";
import { Box, Typography, Button } from "@mui/material";
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
  Paid
} from "@mui/icons-material";
// Menu Items
const menuItems = [
  { title: "Customer Accounts", icon: AccountCircle, path: "/admin/customers" },
  { title: "Customer Profiles", icon: PeopleAlt, path: "/admin/profiles" },
  { title: "Resolved Tickets", icon: Receipt, path: "/admin/tickets" },
  { title: "Subscribed Plan Search", icon: Subscriptions, path: "/admin/subscribed-plan" },
  { title: "Account Usage", icon: DataUsage, path: "/admin/account-usage" },
  { title: "Remove Benefits", icon: Loyalty, path: "/admin/remove-benefits" },
  { title: "SMS Offers", icon: Sms, path: "/admin/sms-offers" },
  { title: "Wallets Details", icon: Wallet, path: "/admin/wallets" },
  { title: "Payments", icon: Payment, path: "/admin/payments" },
  { title: "Cashback Amount", icon: Wallet, path: "/admin/cashback-amount" },
  { title: "Wallet Linking", icon: Wallet, path: "/admin/wallet-linking" },
  { title: "Update Points", icon: Loyalty, path: "/admin/update-points" },
];

const SidebarLayout = ({ children }) => {
  const [isSidebarOpen, setSidebarOpen] = useState(true);
  const location = useLocation(); // Hook to get the current path

  const isActive = (path) => location.pathname === path; // Helper function to check active path
  const { signOut } = useContext(AuthContext);

  const handleSignOut = () => {
    signOut();
    window.location.href = "/";  // Redirect to the root
  };

  return (
    <Box sx={{ display: "flex" }}>
      {/* Sidebar */}
      {isSidebarOpen && (
        <Sidebar aria-label="Custom Themed Sidebar" style={{ height: "100vh" }}>
          <Sidebar.Logo sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', fontSize: '20px', fontWeight: 'bold' }}>
            Telecom 1
          </Sidebar.Logo>

          <Sidebar.Items>
            {/* Grouping items */}
            <Sidebar.ItemGroup>
              {/* Group 1: Stores */}
              <Sidebar.Collapse icon={LocalGroceryStore} label="Stores">
                <Sidebar.Item href="/admin/stores" icon={Store} className={isActive("/stores") ? "bg-gray-100 dark:bg-gray-500" : ""}>
                  Physical Stores
                </Sidebar.Item>
                <Sidebar.Item href="/admin/eshops" icon={Store} className={isActive("/eshops") ? "bg-gray-100 dark:bg-gray-500" : ""}>
                  E-Shops
                </Sidebar.Item>
              </Sidebar.Collapse>

              {/* Group 2: Transactions */}
              <Sidebar.Collapse icon={Paid} label="Transactions">
                <Sidebar.Item href="/admin/cashback-transactions" icon={Wallet} className={isActive("/cashback-transactions") ? "bg-gray-100 dark:bg-gray-500" : ""}>
                  Cashback Transactions
                </Sidebar.Item>
                <Sidebar.Item href="/admin/accepted-transactions" icon={Payment} className={isActive("/accepted-transactions") ? "bg-gray-100 dark:bg-gray-500" : ""}>
                  Accepted Transactions
                </Sidebar.Item>
                <Sidebar.Item href="/admin/transaction-average" icon={Payment} className={isActive("/transaction-average") ? "bg-gray-100 dark:bg-gray-500" : ""}>
                  Transaction Average
                </Sidebar.Item>
              </Sidebar.Collapse>
            </Sidebar.ItemGroup>

            {/* Other Items */}
            <Sidebar.ItemGroup>
              {menuItems.map((item, index) => (
                <Sidebar.Item
                  key={index}
                  as="a"
                  href={item.path}
                  icon={item.icon}
                  className={isActive(item.path) ? "bg-gray-100 dark:bg-gray-500" : ""}
                >
                  {item.title}
                </Sidebar.Item>
              ))}
            </Sidebar.ItemGroup>

            {/* Sign Out */}
            <Sidebar.ItemGroup>
              <Sidebar.Item
                as="button"
                onClick={handleSignOut}
                icon={ExitToApp}
                className="text-red-600 hover:bg-red-100"
                iconClassName="!text-red-600"
              >
                Sign Out
              </Sidebar.Item>
            </Sidebar.ItemGroup>
          </Sidebar.Items>
        </Sidebar>
      )}

      {/* Main Content */}
            <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3, // Padding inside the content
          marginLeft: isSidebarOpen ? "0px" : "0px", // Adjusts when the sidebar is open
          transition: "margin-left 0.3s ease", // Smooth transition for sidebar toggling
          minHeight: "100vh", // Ensure it stretches to the full viewport height
          backgroundColor: "#fafafa", // Optional: Matches the rest of the layout background
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
