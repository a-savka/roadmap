import { Outlet } from 'react-router-dom';
import { useAppConfigContext } from '../context/AppConfigContext';
import useUserStore from '../store/userStore';
import { useNavigate } from 'react-router-dom';

const Layout = () => {
  const { t, isLoading } = useAppConfigContext();
  const user = useUserStore((state) => state.user);
  const logout = useUserStore((state) => state.logout);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  if (isLoading) {
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
        <nav className="navbar navbar-light bg-light border-bottom">
          <div className="container-fluid">
            <span className="navbar-brand mb-0 h1">{t('navbar.brand')}</span>
            {user && (
              <div className="d-flex align-items-center gap-2">
                <span className="text-muted">{user.username}</span>
                <button className="btn btn-outline-secondary btn-sm" onClick={handleLogout}>
                  Выйти
                </button>
              </div>
            )}
          </div>
        </nav>
      </header>
      <main className="container d-flex flex-grow-1">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
