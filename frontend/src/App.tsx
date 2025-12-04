import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import FormPage from './pages/FormPage';
import FormDetailPage from './pages/FormDetailPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="form" element={<FormPage />} />
        <Route path="form-details/:formId" element={<FormDetailPage />} />
      </Route>
    </Routes>
  );
}

export default App;
