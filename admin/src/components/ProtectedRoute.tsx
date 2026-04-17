import type { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import useAdminStore from '../store/adminStore';

interface ProtectedRouteProps {
  children: ReactNode;
}

export const AdminProtectedRoute = ({ children }: ProtectedRouteProps) => {
  const isAuthenticated = useAdminStore((s) => s.isAuthenticated);
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />;
};