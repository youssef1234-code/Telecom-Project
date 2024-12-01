import React, { useState, useContext } from "react";
import { Sidebar } from "flowbite-react";
import { Route, Routes, useLocation } from "react-router-dom";
import { AuthContext } from "../App";
import { Box, Typography, Button } from "@mui/material";
import {
  ExitToApp,
  AccountCircle,
  QueryStats,
  Payment,
  Loyalty,
  Sms,
  Store,
  ListAlt,
  MoneyOffCsred,
  Receipt,
  Cached,
  Redeem,
} from "@mui/icons-material";

// Menu Items for Customer Pages
const customerMenuItems = [
  // Part 1
  { title: "Service Plans", icon: ListAlt, path: "/customer/service-plans" },
  {
    title: "Usage Consumption",
    icon: QueryStats,
    path: "/customer/usage-consumption",
  },
  {
    title: "Unsubscribed Plans",
    icon: Sms,
    path: "/customer/unsubscribed-plans",
  },
  {
    title: "Active Plans Usage",
    icon: QueryStats,
    path: "/customer/active-plans-usage",
  },
  {
    title: "Cashback Transactions",
    icon: Loyalty,
    path: "/customer/cashback-transactions",
  },
  // Part 2
  { title: "Active Benefits", icon: Loyalty, path: "/customer/active-benefits" },
  {
    title: "Unresolved Tickets",
    icon: Receipt,
    path: "/customer/unresolved-tickets",
  },
  {
    title: "Highest Value Voucher",
    icon: Redeem,
    path: "/customer/highest-voucher",
  },
  {
    title: "Remaining Payment",
    icon: Payment,
    path: "/customer/remaining-payment",
  },
  {
    title: "Extra Payment",
    icon: Payment,
    path: "/customer/extra-payment",
  },
  {
    title: "Top Payments",
    icon: MoneyOffCsred,
    path: "/customer/top-payments",
  },
  // Part 3
  { title: "All Shops", icon: Store, path: "/customer/all-shops" },
  {
    title: "Past Subscriptions",
    icon: ListAlt,
    path: "/customer/past-subscriptions",
  },
  {
    title: "Renew Subscription",
    icon: Cached,
    path: "/customer/renew-subscription",
  },
  {
    title: "Cashback Amount",
    icon: Loyalty,
    path: "/customer/cashback-amount",
  },
  {
    title: "Recharge Balance",
    icon: Payment,
    path: "/customer/recharge-balance",
  },
  { title: "Redeem Voucher", icon: Redeem, path: "/customer/redeem-voucher" },
];

const CustomerSidebar = ({ children }) => {
  const [isSidebarOpen, setSidebarOpen] = useState(true);
  const location = useLocation(); // Hook to get the current path
  const { signOut } = useContext(AuthContext);

  const isActive = (path) => location.pathname === path; // Helper function to check active path

  const handleSignOut = () => {
    signOut();
    window.location.href = "/customer"; // Redirect to customer base path
  };

  return (
    <Box sx={{ display: "flex" }}>
      {/* Sidebar */}
      {isSidebarOpen && (
        <Sidebar aria-label="Customer Sidebar" style={{ height: "100vh" }}>
          <Sidebar.Logo
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              fontSize: "20px",
              fontWeight: "bold",
            }}
          >
            Customer Portal
          </Sidebar.Logo>

          <Sidebar.Items>
            {/* Menu Items */}
            <Sidebar.ItemGroup>
              {customerMenuItems.map((item, index) => (
                <Sidebar.Item
                  key={index}
                  as="a"
                  href={item.path}
                  icon={item.icon}
                  className={
                    isActive(item.path) ? "bg-gray-100 dark:bg-gray-500" : ""
                  }
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
          p: 3,
          transition: "margin-left 0.3s ease",
          minHeight: "100vh",
          backgroundColor: "#fafafa",
        }}
      >
        <Routes>
          {customerMenuItems.map((item, index) => (
            <Route
              key={index}
              path={item.path}
              element={
                <Typography variant="h4">{item.title} Page Placeholder</Typography>
              }
            />
          ))}
        </Routes>
        {children}
      </Box>
    </Box>
  );
};

export default CustomerSidebar;
