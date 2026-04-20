import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getStepConditions, createStepCondition, deleteStepCondition } from '../services/api';
import type { StepCondition } from '../types';

const StepConditionsPage = () => {
  const { stepName } = useParams();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const [showForm, setShowForm] = useState(false);
  const [newCondition, setNewCondition] = useState({
    stepName: stepName || '',
    countryCode: null as string | null,
    visitPurpose: null as string | null,
    relocationProgramStatus: null as string | null,
    hqsStatus: null as string | null,
  });

  const { data: conditions = [], isLoading } = useQuery({
    queryKey: ['stepConditions', stepName],
    queryFn: () => getStepConditions(stepName!),
    enabled: !!stepName,
  });

  const createMutation = useMutation({
    mutationFn: (data: typeof newCondition) => createStepCondition(stepName!, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['stepConditions', stepName] });
      setShowForm(false);
      setNewCondition({
        stepName: stepName || '',
        countryCode: null,
        visitPurpose: null,
        relocationProgramStatus: null,
        hqsStatus: null,
      });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => deleteStepCondition(stepName!, id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['stepConditions', stepName] });
    },
  });

  const handleCreate = (e: React.FormEvent) => {
    e.preventDefault();
    createMutation.mutate(newCondition);
  };

  const handleDelete = (id: number) => {
    if (confirm('Удалить условие?')) {
      deleteMutation.mutate(id);
    }
  };

  if (isLoading) return <div>Загрузка...</div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Условия шага "{stepName}"</h2>
        <button className="btn btn-secondary" onClick={() => navigate('/steps')}>
          Назад к шагам
        </button>
      </div>

      <button className="btn btn-success mb-3" onClick={() => setShowForm(!showForm)}>
        {showForm ? 'Отмена' : 'Добавить условие'}
      </button>

      {showForm && (
        <form onSubmit={handleCreate} className="card card-body mb-4">
          <div className="row">
            <div className="col-md-3 mb-3">
              <label className="form-label">Код страны</label>
              <input
                type="text"
                className="form-control"
                value={newCondition.countryCode || ''}
                onChange={(e) =>
                  setNewCondition({
                    ...newCondition,
                    countryCode: e.target.value || null,
                  })
                }
              />
            </div>
            <div className="col-md-3 mb-3">
              <label className="form-label">Цель визита</label>
              <input
                type="text"
                className="form-control"
                value={newCondition.visitPurpose || ''}
                onChange={(e) =>
                  setNewCondition({
                    ...newCondition,
                    visitPurpose: e.target.value || null,
                  })
                }
              />
            </div>
            <div className="col-md-3 mb-3">
              <label className="form-label">Статус ПП</label>
              <input
                type="text"
                className="form-control"
                value={newCondition.relocationProgramStatus || ''}
                onChange={(e) =>
                  setNewCondition({
                    ...newCondition,
                    relocationProgramStatus: e.target.value || null,
                  })
                }
              />
            </div>
            <div className="col-md-3 mb-3">
              <label className="form-label">Статус HQ</label>
              <input
                type="text"
                className="form-control"
                value={newCondition.hqsStatus || ''}
                onChange={(e) =>
                  setNewCondition({
                    ...newCondition,
                    hqsStatus: e.target.value || null,
                  })
                }
              />
            </div>
          </div>
          <button type="submit" className="btn btn-primary">
            Добавить
          </button>
        </form>
      )}

      {conditions.length === 0 ? (
        <p>Нет условий. Добавьте первое условие.</p>
      ) : (
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Код страны</th>
              <th>Цель визита</th>
              <th>Программа переселения</th>
              <th>Статус HQ</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {conditions.map((condition: StepCondition) => (
              <tr key={condition.id}>
                <td>{condition.id}</td>
                <td>{condition.countryCode ?? '-'}</td>
                <td>{condition.visitPurpose ?? '-'}</td>
                <td>{condition.relocationProgramStatus ?? '-'}</td>
                <td>{condition.hqsStatus ?? '-'}</td>
                <td>
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => condition.id && handleDelete(condition.id)}
                  >
                    Удалить
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default StepConditionsPage;