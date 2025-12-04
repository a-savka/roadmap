import { Outlet } from 'react-router-dom';

const Layout = () => {
  return (
    <div className="d-flex flex-column vh-100">
      <header>
        <nav className="navbar navbar-light bg-light border-bottom">
          <div className="container-fluid">
            <span className="navbar-brand mb-0 h1">Дорожная карта</span>
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