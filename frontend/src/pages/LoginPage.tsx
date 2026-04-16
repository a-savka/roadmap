import { useState } from 'react';
import type { FormEvent } from 'react';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../store/userStore';
import { useAppConfigContext } from '../context/AppConfigContext';
import type { User } from '../types';

const loginUser = async (userData: { username: string; password: string }): Promise<User> => {
  const response = await fetch('/api/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  });

  if (response.status === 404) {
    const registerResponse = await fetch('/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    if (!registerResponse.ok) {
      throw new Error('Ошибка регистрации');
    }
    return registerResponse.json();
  }

  if (!response.ok) {
    throw new Error('Ошибка входа');
  }

  return response.json();
};

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const setUser = useUserStore((state) => state.setUser);
  const { t } = useAppConfigContext();

  const mutation = useMutation({
    mutationFn: loginUser,
    onSuccess: (data) => {
      console.log('Успех:', data);
      setUser(data);
      navigate('/form');
    },
    onError: (error) => {
      setError(error.message);
    },
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (!username || !password) {
      setError(t('login.validation.error'));
      return;
    }
    setError('');
    mutation.mutate({ username, password });
  };

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <div className="bg-light p-4 border rounded" style={{ width: '400px' }}>
        <h2 className="text-center mb-4">{t('login.page.title')}</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">{t('login.username.label')}</label>
            <input
              type="text"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">{t('login.password.label')}</label>
            <input
              type="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          {error && <div className="alert alert-danger">{error}</div>}
          <button type="submit" className="btn btn-primary w-100" disabled={mutation.isPending}>
            {mutation.isPending ? t('login.button.pending') : t('login.button.text')}
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
