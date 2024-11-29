import React, { createContext, useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import AdminSignIn from "./components/AdminSignIn";
import AdminCustomersPage from "./components/pages/AdminCustomerPage"; 
import AdminProfilePage from "./components/pages/AdminprofilePage"; 
import StoreVouchersPage from "./components/pages/StoreVouchersPage"; 
import PlanSubiscriptionPageWithDate from "./components/pages/PlanSubiscriptionPageWithDate"; 
import ResolvedTicketsPage from "./components/pages/ResolvedTicketsPage"; 
import NotFoundPage from "./components/NotFoundPage";
import WalletDetailsPage from "./components/pages/WalletDetailsPage";
import EShopTablePage from "./components/pages/EShopTablePage";

// Create a Context for Authentication
export const AuthContext = createContext();

function App() {
  const storedToken = localStorage.getItem("token");
  const storedRole = localStorage.getItem("role");

  const [auth, setAuth] = useState({
    token: storedToken || null,
    role: storedRole || null,
  });

  useEffect(() => {
    if (auth.token && auth.role) {
      localStorage.setItem("token", auth.token);
      localStorage.setItem("role", auth.role);
    } else {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
    }
  }, [auth]);

  const signOut = () => {
    setAuth({ token: null, role: null });
  };

  return (
    <AuthContext.Provider value={{ auth, setAuth, signOut }}>
      <Router>
        <Routes>
          <Route path="/" element={<AdminSignIn />} />
          <Route
          path="/admin/customers"
          element={
            <ProtectedRoute role="admin">
              <AdminCustomersPage />
            </ProtectedRoute>
          }
          />
          <Route
          path="/admin/profiles"
          element={
            <ProtectedRoute role="admin">
              <AdminProfilePage />
            </ProtectedRoute>
          }
          />
          <Route
          path="/admin/stores"
          element={
            <ProtectedRoute role="admin">
              <StoreVouchersPage />
            </ProtectedRoute>
          }
          />
          <Route
          path="/admin/tickets"
          element={
            <ProtectedRoute role="admin">
              <ResolvedTicketsPage />
            </ProtectedRoute>
          }
          />

          <Route
          path="/admin/subscribed-plan"
          element={
            <ProtectedRoute role="admin">
              <PlanSubiscriptionPageWithDate />
            </ProtectedRoute>
          }
          />

          <Route
          path="/admin/wallets"
          element={
            <ProtectedRoute role="admin">
              <WalletDetailsPage />
            </ProtectedRoute>
          }
          />

          <Route
          path="/admin/eshops"
          element={
            <ProtectedRoute role="admin">
              <EShopTablePage />
            </ProtectedRoute>
          }
          />

          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

// Protected Route Component
function ProtectedRoute({ children, role }) {
  const { auth } = React.useContext(AuthContext);

  if (!auth.token || auth.role !== role) {
    return <Navigate to="/" replace />;
  }

  return children;
}

export default App;
