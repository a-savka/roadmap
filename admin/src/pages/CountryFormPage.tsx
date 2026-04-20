import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getCountry, createCountry, updateCountry } from '../services/api';

const CountryFormPage = () => {
  const navigate = useNavigate();
  const { code } = useParams();
  const isEdit = code && code !== 'new';
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    code: '',
    name: '',
  });

  const { data: country, isLoading } = useQuery({
    queryKey: ['country', code],
    queryFn: () => getCountry(code!),
    enabled: !!isEdit && !!code,
  });

  useEffect(() => {
    if (country && isEdit) {
      setFormData(country);
    }
  }, [country, isEdit]);

  const createMutation = useMutation({
    mutationFn: createCountry,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['countries'] });
      navigate('/countries');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data: typeof formData) => updateCountry(code!, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['countries'] });
      navigate('/countries');
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (isEdit) {
      updateMutation.mutate(formData);
    } else {
      createMutation.mutate(formData);
    }
  };

  if (isEdit && isLoading) return <div>Загрузка...</div>;

  return (
    <div className="form-page">
      <h2 className="mb-4">{isEdit ? 'Редактирование страны' : 'Создание страны'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Код страны</label>
          <input
            type="text"
            className="form-control"
            value={formData.code}
            onChange={(e) => setFormData({ ...formData, code: e.target.value })}
            required
            disabled={isEdit}
            maxLength={3}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Название</label>
          <input
            type="text"
            className="form-control"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary me-2">
          {isEdit ? 'Сохранить' : 'Создать'}
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => navigate('/countries')}
        >
          Отмена
        </button>
      </form>
    </div>
  );
};

export default CountryFormPage;