import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Link, useNavigate } from 'react-router-dom';
import { getSteps, deleteStep } from '../services/api';
import type { Step } from '../types';

const StepsListPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: steps = [], isLoading } = useQuery({
    queryKey: ['steps'],
    queryFn: getSteps,
  });

  const deleteMutation = useMutation({
    mutationFn: deleteStep,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['steps'] });
    },
  });

  const handleDelete = (stepName: string) => {
    if (confirm(`Удалить шаг "${stepName}"?`)) {
      deleteMutation.mutate(stepName);
    }
  };

  if (isLoading) return <div>Загрузка...</div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Шаги</h2>
        <Link to="/steps/new" className="btn btn-success">
          Добавить шаг
        </Link>
      </div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Название</th>
            <th>Описание</th>
            <th>Порядок</th>
            <th>Дней до дедлайна</th>
            <th>Включён</th>
            <th>Условия</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {steps.map((step: Step) => (
            <tr key={step.stepName}>
              <td>{step.stepName}</td>
              <td>{step.stepDescription}</td>
              <td>{step.stepOrder}</td>
              <td>{step.deadlineDays ?? '-'}</td>
              <td>{step.enabled ? 'Да' : 'Нет'}</td>
              <td>
                <Link
                  to={`/steps/${step.stepName}/conditions`}
                  className="btn btn-sm btn-info me-2"
                >
                  Условия
                </Link>
              </td>
              <td>
                <button
                  className="btn btn-sm btn-primary me-2"
                  onClick={() => navigate(`/steps/${step.stepName}`)}
                >
                  Редактировать
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(step.stepName)}
                >
                  Удалить
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StepsListPage;