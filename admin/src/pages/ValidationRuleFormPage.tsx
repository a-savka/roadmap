import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getValidationRule, createValidationRule, updateValidationRule } from '../services/api';

const ValidationRuleFormPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEdit = id && id !== 'new';
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    id: 0,
    countryCode: null as string | null,
    visitPurpose: null as string | null,
    relocationProgramStatus: null as string | null,
    hqsStatus: null as string | null,
    maxDays: 30,
  });

  const { data: rule, isLoading } = useQuery({
    queryKey: ['validationRule', id],
    queryFn: () => getValidationRule(parseInt(id!)),
    enabled: !!isEdit && !!id,
  });

  useEffect(() => {
    if (rule && isEdit) {
      setFormData(rule);
    }
  }, [rule, isEdit]);

  const createMutation = useMutation({
    mutationFn: createValidationRule,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['validationRules'] });
      navigate('/validation-rules');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data: typeof formData) => updateValidationRule(parseInt(id!), data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['validationRules'] });
      navigate('/validation-rules');
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
      <h2 className="mb-4">{isEdit ? 'Редактирование правила' : 'Создание правила'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Код страны</label>
          <input
            type="text"
            className="form-control"
            value={formData.countryCode || ''}
            onChange={(e) =>
              setFormData({ ...formData, countryCode: e.target.value || null })
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Цель визита</label>
          <input
            type="text"
            className="form-control"
            value={formData.visitPurpose || ''}
            onChange={(e) =>
              setFormData({ ...formData, visitPurpose: e.target.value || null })
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Программа переселения</label>
          <input
            type="text"
            className="form-control"
            value={formData.relocationProgramStatus || ''}
            onChange={(e) =>
              setFormData({ ...formData, relocationProgramStatus: e.target.value || null })
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Статус HQ</label>
          <input
            type="text"
            className="form-control"
            value={formData.hqsStatus || ''}
            onChange={(e) =>
              setFormData({ ...formData, hqsStatus: e.target.value || null })
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Максимальное количество дней</label>
          <input
            type="number"
            className="form-control"
            value={formData.maxDays}
            onChange={(e) =>
              setFormData({ ...formData, maxDays: parseInt(e.target.value) })
            }
            required
          />
        </div>
        <button type="submit" className="btn btn-primary me-2">
          {isEdit ? 'Сохранить' : 'Создать'}
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => navigate('/validation-rules')}
        >
          Отмена
        </button>
      </form>
    </div>
  );
};

export default ValidationRuleFormPage;