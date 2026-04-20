import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Link, useNavigate } from 'react-router-dom';
import { getValidationRules, deleteValidationRule } from '../services/api';

const ValidationRulesListPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: rules = [], isLoading } = useQuery({
    queryKey: ['validationRules'],
    queryFn: getValidationRules,
  });

  const deleteMutation = useMutation({
    mutationFn: deleteValidationRule,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['validationRules'] });
    },
  });

  const handleDelete = (id: number) => {
    if (confirm('Удалить правило?')) {
      deleteMutation.mutate(id);
    }
  };

  if (isLoading) return <div>Загрузка...</div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Правила валидации</h2>
        <Link to="/validation-rules/new" className="btn btn-success">
          Добавить правило
        </Link>
      </div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Код страны</th>
            <th>Цель визита</th>
            <th>Программа переселения</th>
            <th>Статус HQ</th>
            <th>Макс. дней</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {rules.map((rule) => (
            <tr key={rule.id}>
              <td>{rule.id}</td>
              <td>{rule.countryCode ?? '-'}</td>
              <td>{rule.visitPurpose ?? '-'}</td>
              <td>{rule.relocationProgramStatus ?? '-'}</td>
              <td>{rule.hqsStatus ?? '-'}</td>
              <td>{rule.maxDays}</td>
              <td>
                <button
                  className="btn btn-sm btn-primary me-2"
                  onClick={() => navigate(`/validation-rules/${rule.id}`)}
                >
                  ✏️
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(rule.id)}
                >
                  🗑️
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ValidationRulesListPage;