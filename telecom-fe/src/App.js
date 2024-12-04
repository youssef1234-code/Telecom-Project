import React, { createContext, useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import CustomerSignIn from "./components/CustomerSignIn";
import AdminSignIn from "./components/AdminSignIn";
import AdminCustomersPage from "./components/pages/AdminCustomerPage";
import AdminProfilePage from "./components/pages/AdminprofilePage";
import StoreVouchersPage from "./components/pages/StoreVouchersPage";
import PlanSubiscriptionPageWithDate from "./components/pages/PlanSubiscriptionPageWithDate";
import ResolvedTicketsPage from "./components/pages/ResolvedTicketsPage";
import NotFoundPage from "./components/NotFoundPage";
import WalletDetailsPage from "./components/pages/WalletDetailsPage";
import EShopTablePage from "./components/pages/EShopTablePage";
import AccountPaymentsPage from "./components/pages/AccountPaymentsPage";
import UsageSumPage from "./components/pages/UsageSumPage";
import SmsOffersPage from "./components/pages/SmsOffersPage";
import DeleteBenefitsPage from "./components/pages/DeleteBenefitsPage";
import ActiveBenefitsPage from "./components/pages/ActiveBenefitsPage";
import CashbackTransactionsTablePage from "./components/pages/CashbackTransactionsTablePage";
import AcceptedTransactionsPage from "./components/pages/AcceptedTranscationsPage";
import WalletCashbackPage from "./components/pages/WalletCashbackPage";
import AverageTransactionsPage from "./components/pages/AverageTransactionsPage";
import ConsumptionPage from "./components/pages/ConsumptionPage";
import UnsubscribedPage from "./components/pages/UnsubscribedPage";
import WalletLinkingPage from "./components/pages/WalletLinkingPage";
import UpdatePointsPage from "./components/pages/UpdatePointsPage";
import MonthUsagePage from "./components/pages/MonthUsagePage";
import CashbackPage from "./components/pages/CashbackPage";
import AllBenefitsPage from "./components/pages/AllBenefitsPage";

// Create a Context for Authentication
export const AuthContext = createContext();

function App() {
  const storedToken = localStorage.getItem("token");
  const storedRole = localStorage.getItem("role");

  //const storedNumber = localStorage.getItem("mobileNumber");

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
      localStorage.removeItem("mobileNumber");
    }
  }, [auth]);

  const signOut = () => {
    setAuth({ token: null, role: null, mobileNumber: null });
  };

  return (
    <AuthContext.Provider value={{ auth, setAuth, signOut }}>
      <Router>
        <Routes>
          <Route path="/" element={<AdminSignIn />} />
          <Route path="/customer" element={<CustomerSignIn />} />
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
            path="/admin/account-usage"
            element={
              <ProtectedRoute role="admin">
                <UsageSumPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/sms-offers"
            element={
              <ProtectedRoute role="admin">
                <SmsOffersPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/payments"
            element={
              <ProtectedRoute role="admin">
                <AccountPaymentsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/remove-benefits"
            element={
              <ProtectedRoute role="admin">
                <DeleteBenefitsPage />
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

          <Route
            path="/admin/payments"
            element={
              <ProtectedRoute role="admin">
                <AccountPaymentsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/customer/active-benefits"
            element={
              <ProtectedRoute role="customer">
                <ActiveBenefitsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/cashback-transactions"
            element={
              <ProtectedRoute role="admin">
                <CashbackTransactionsTablePage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/accepted-transactions"
            element={
              <ProtectedRoute role="admin">
                <AcceptedTransactionsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/cashback-amount"
            element={
              <ProtectedRoute role="admin">
                <WalletCashbackPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/transaction-average"
            element={
              <ProtectedRoute role="admin">
                <AverageTransactionsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/customer/usage-consumption"
            element={
              <ProtectedRoute role="customer">
                <ConsumptionPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/customer/unsubscribed-plans"
            element={
              <ProtectedRoute role="customer">
                <UnsubscribedPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/wallet-linking"
            element={
              <ProtectedRoute role="admin">
                <WalletLinkingPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/admin/update-points"
            element={
              <ProtectedRoute role="admin">
                <UpdatePointsPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/customer/active-plans-usage"
            element={
              <ProtectedRoute role="customer">
                <MonthUsagePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/customer/cashback-transactions"
            element={
              <ProtectedRoute role="customer">
                <CashbackPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/customer/active-benefits"
            element={
              <ProtectedRoute role="customer">
                <CashbackPage />
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

// Sample data for Account Plans Usage
// 01012345678
// 01/01/2024

//Sample Data for Plan Subiscriptions On Date
// 01/01/2024
// 1

// SMS offers
// 01012345678
