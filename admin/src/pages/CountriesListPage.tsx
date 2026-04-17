import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Link, useNavigate } from 'react-router-dom';
import { getCountries, deleteCountry } from '../services/api';

const CountriesListPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: countries = [], isLoading } = useQuery({
    queryKey: ['countries'],
    queryFn: getCountries,
  });

  const deleteMutation = useMutation({
    mutationFn: deleteCountry,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['countries'] });
    },
  });

  const handleDelete = (code: string) => {
    if (confirm(`Удалить страну "${code}"?`)) {
      deleteMutation.mutate(code);
    }
  };

  if (isLoading) return <div>Загрузка...</div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Страны</h2>
        <Link to="/countries/new" className="btn btn-success">
          Добавить страну
        </Link>
      </div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Код</th>
            <th>Название</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {countries.map((country) => (
            <tr key={country.code}>
              <td>{country.code}</td>
              <td>{country.name}</td>
              <td>
                <button
                  className="btn btn-sm btn-primary me-2"
                  onClick={() => navigate(`/countries/${country.code}`)}
                >
                  Редактировать
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(country.code)}
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

export default CountriesListPage;