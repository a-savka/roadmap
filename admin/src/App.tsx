import { Routes, Route, Navigate } from 'react-router-dom';
import useAdminStore from './store/adminStore';
import { AdminProtectedRoute } from './components/ProtectedRoute';
import AdminLayout from './components/AdminLayout';
import AdminLoginPage from './pages/AdminLoginPage';
import AppRoutes from './pages/Routes';

function App() {
  const isAuthenticated = useAdminStore((state) => state.isAuthenticated);

  return (
    <Routes>
      <Route
        path="/login"
        element={isAuthenticated ? <Navigate to="/" /> : <AdminLoginPage />}
      />
      <Route
        path="/*"
        element={
          <AdminProtectedRoute>
            <AdminLayout>
              <AppRoutes />
            </AdminLayout>
          </AdminProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;