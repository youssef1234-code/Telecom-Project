import React, { createContext, useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import AdminSignIn from "./components/AdminSignIn";
import AdminDashboard from "./components/AdminDashboard"; // Assume you have an AdminDashboard component
import NotFoundPage from "./components/NotFoundPage";

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

  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      <Router>
        <Routes>
          <Route path="/" element={<AdminSignIn />} />
          <Route
            path="/admin/*"
            element={
              <ProtectedRoute role="admin">
                <AdminDashboard />
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
