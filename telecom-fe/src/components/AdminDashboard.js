import React, { useState } from "react";
import { Route, Routes, Link } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  IconButton,
  CssBaseline,
  Typography,
  Box,
} from "@mui/material";
import {
  Menu as MenuIcon,
  AccountCircle,
  Store,
  Receipt,
  Loyalty,
  Sms,
  Payment,
  Wallet,
} from "@mui/icons-material";

// Define menu items and their corresponding paths
const menuItems = [
  { title: 'Customer Profiles', icon: <AccountCircle />, path: '/customers' },
  { title: 'Physical Stores', icon: <Store />, path: '/stores' },
  { title: 'Resolved Tickets', icon: <Receipt />, path: '/tickets' },
  { title: 'Customer Accounts', icon: <Loyalty />, path: '/accounts' },
  { title: 'Subscribed Plan Search', icon: <Loyalty />, path: '/subscribed-plan' },
  { title: 'Account Usage', icon: <Loyalty />, path: '/account-usage' },
  { title: 'Remove Benefits', icon: <Loyalty />, path: '/remove-benefits' },
  { title: 'SMS Offers', icon: <Sms />, path: '/sms-offers' },
  { title: 'Wallets Details', icon: <Wallet />, path: '/wallets' },
  { title: 'E-Shops', icon: <Store />, path: '/eshops' },
  { title: 'Payments', icon: <Payment />, path: '/payments' },
  { title: 'Cashback Transactions', icon: <Wallet />, path: '/cashback-transactions' },
  { title: 'Accepted Transactions', icon: <Payment />, path: '/accepted-transactions' },
  { title: 'Cashback Amount', icon: <Wallet />, path: '/cashback-amount' },
  { title: 'Transaction Average', icon: <Payment />, path: '/transaction-average' },
  { title: 'Wallet Linking', icon: <Wallet />, path: '/wallet-linking' },
  { title: 'Update Points', icon: <Loyalty />, path: '/update-points' },
];

const AdminDashboard = () => {
  const [isDrawerOpen, setDrawerOpen] = useState(true);

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <IconButton
            color="inherit"
            edge="start"
            onClick={() => setDrawerOpen(!isDrawerOpen)}
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            Admin Dashboard
          </Typography>
        </Toolbar>
      </AppBar>

      <Drawer
        variant="persistent"
        open={isDrawerOpen}
        sx={{
          width: 240,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: 240, boxSizing: "border-box" },
        }}
      >
        <Toolbar />
        <List>
          {menuItems.map((item, index) => (
            <ListItem button key={index} component={Link} to={item.path}>
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.title} />
            </ListItem>
          ))}
        </List>
      </Drawer>

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          marginLeft: isDrawerOpen ? "240px" : "0px",
        }}
      >
        <Toolbar />
        <Routes>
          {menuItems.map((item, index) => (
            <Route
              key={index}
              path={item.path}
              element={<Typography variant="h4">{item.title}</Typography>}
            />
          ))}
        </Routes>
      </Box>
    </Box>
  );
};

export default AdminDashboard;
