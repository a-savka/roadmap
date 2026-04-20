import type { ReactNode } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useAdminStore from '../store/adminStore';

interface AdminLayoutProps {
  children: ReactNode;
}

const AdminLayout = ({ children }: AdminLayoutProps) => {
  const user = useAdminStore((s) => s.isAuthenticated);
  const logout = useAdminStore((s) => s.logout);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!user) {
    return (
      <div className="d-flex flex-column vh-100">
        <main className="container d-flex flex-grow-1 justify-content-center align-items-center">
          <p>Загрузка...</p>
        </main>
      </div>
    );
  }

  return (
    <div className="d-flex flex-column vh-100">
      <header>
        <nav className="navbar navbar-expand-lg navbar-light bg-light border-bottom">
          <div className="container-fluid">
            <Link to="/" className="navbar-brand mb-0 h1">ДK - Админ</Link>
            <div className="collapse navbar-collapse">
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <Link className="nav-link" to="/">
                    Панель
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/steps">
                    Шаги
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/validation-rules">
                    Правила
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/messages">
                    Сообщения
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/countries">
                    Страны
                  </Link>
                </li>
              </ul>
            </div>
            <div className="d-flex align-items-center gap-2">
              <span className="text-muted">admin</span>
              <button className="btn btn-outline-secondary btn-sm" onClick={handleLogout}>
                Выйти
              </button>
            </div>
          </div>
        </nav>
      </header>
      <main className="container d-flex flex-grow-1 py-4">
        {children}
      </main>
    </div>
  );
};

export default AdminLayout;