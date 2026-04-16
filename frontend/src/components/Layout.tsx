import { Outlet } from 'react-router-dom';
import { useAppConfigContext } from '../context/AppConfigContext';

const Layout = () => {
  const { t, isLoading } = useAppConfigContext();

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