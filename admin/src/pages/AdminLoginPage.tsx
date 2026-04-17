import { useState } from 'react';
import type { FormEvent } from 'react';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { adminLogin } from '../services/api';
import useAdminStore from '../store/adminStore';

const AdminLoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const login = useAdminStore((state) => state.login);

  const mutation = useMutation({
    mutationFn: () => adminLogin({ username, password }),
    onSuccess: (data) => {
      if (data?.token) {
        login(data.token);
        navigate('/');
      }
    },
    onError: (err: Error) => {
      setError(err.message || 'Неверные учётные данные');
    },
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (!username || !password) {
      setError('Имя пользователя и пароль обязательны');
      return;
    }
    setError('');
    mutation.mutate();
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div className="bg-light p-4 border rounded" style={{ width: '400px' }}>
        <h2 className="text-center mb-4">Вход администратора</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Имя пользователя</label>
            <input
              type="text"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Пароль</label>
            <input
              type="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          {error && <div className="alert alert-danger">{error}</div>}
          <button type="submit" className="btn btn-primary w-100" disabled={mutation.isPending}>
            {mutation.isPending ? 'Вход...' : 'Войти'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLoginPage;