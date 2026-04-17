import { Navigate, Outlet } from 'react-router-dom';
import useAdminStore from '../store/adminStore';

const ProtectedRoute = () => {
  const isAuthenticated = useAdminStore((s) => s.isAuthenticated);
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;