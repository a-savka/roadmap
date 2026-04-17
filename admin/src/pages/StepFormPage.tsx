import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getStep, createStep, updateStep } from '../services/api';

const StepFormPage = () => {
  const navigate = useNavigate();
  const { stepName } = useParams();
  const isEdit = stepName && stepName !== 'new';
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    stepName: '',
    stepDescription: '',
    stepOrder: 0,
    enabled: true,
    deadlineDays: null as number | null,
  });

  const { data: step, isLoading } = useQuery({
    queryKey: ['step', stepName],
    queryFn: () => getStep(stepName!),
    enabled: !!isEdit,
  });

  useEffect(() => {
    if (step && isEdit) {
      setFormData({
        stepName: step.stepName,
        stepDescription: step.stepDescription,
        stepOrder: step.stepOrder,
        enabled: step.enabled,
        deadlineDays: step.deadlineDays,
      });
    }
  }, [step, isEdit]);

  const createMutation = useMutation({
    mutationFn: createStep,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['steps'] });
      navigate('/steps');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data: typeof formData) => updateStep(stepName!, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['steps'] });
      navigate('/steps');
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
    <div>
      <h2 className="mb-4">{isEdit ? 'Редактирование шага' : 'Создание шага'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Название</label>
          <input
            type="text"
            className="form-control"
            value={formData.stepName}
            onChange={(e) => setFormData({ ...formData, stepName: e.target.value })}
            required
            disabled={isEdit}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Описание</label>
          <textarea
            className="form-control"
            value={formData.stepDescription}
            onChange={(e) => setFormData({ ...formData, stepDescription: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Порядок</label>
          <input
            type="number"
            className="form-control"
            value={formData.stepOrder}
            onChange={(e) => setFormData({ ...formData, stepOrder: parseInt(e.target.value) })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Дней до дедлайна</label>
          <input
            type="number"
            className="form-control"
            value={formData.deadlineDays ?? ''}
            onChange={(e) =>
              setFormData({
                ...formData,
                deadlineDays: e.target.value ? parseInt(e.target.value) : null,
              })
            }
          />
        </div>
        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            id="enabled"
            checked={formData.enabled}
            onChange={(e) => setFormData({ ...formData, enabled: e.target.checked })}
          />
          <label className="form-check-label" htmlFor="enabled">
            Включён
          </label>
        </div>
        <button type="submit" className="btn btn-primary me-2">
          {isEdit ? 'Сохранить' : 'Создать'}
        </button>
        <button type="button" className="btn btn-secondary" onClick={() => navigate('/steps')}>
          Отмена
        </button>
      </form>
    </div>
  );
};

export default StepFormPage;