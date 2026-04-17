import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { getSteps, getValidationRules, getAppMessages, getCountries } from '../services/api';

const DashboardPage = () => {
  const { data: steps = [] } = useQuery({
    queryKey: ['steps'],
    queryFn: getSteps,
  });

  const { data: validationRules = [] } = useQuery({
    queryKey: ['validationRules'],
    queryFn: getValidationRules,
  });

  const { data: messages = [] } = useQuery({
    queryKey: ['messages'],
    queryFn: getAppMessages,
  });

  const { data: countries = [] } = useQuery({
    queryKey: ['countries'],
    queryFn: getCountries,
  });

  return (
    <div>
      <h1 className="mb-4">Панель администрирования</h1>
      <div className="row">
        <div className="col-md-6 col-lg-3 mb-3">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Шаги</h5>
              <p className="card-text display-4">{steps.length}</p>
              <Link to="/steps" className="btn btn-primary">
                Управление
              </Link>
            </div>
          </div>
        </div>
        <div className="col-md-6 col-lg-3 mb-3">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Правила валидации</h5>
              <p className="card-text display-4">{validationRules.length}</p>
              <Link to="/validation-rules" className="btn btn-primary">
                Управление
              </Link>
            </div>
          </div>
        </div>
        <div className="col-md-6 col-lg-3 mb-3">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Сообщения</h5>
              <p className="card-text display-4">{messages.length}</p>
              <Link to="/messages" className="btn btn-primary">
                Управление
              </Link>
            </div>
          </div>
        </div>
        <div className="col-md-6 col-lg-3 mb-3">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Страны</h5>
              <p className="card-text display-4">{countries.length}</p>
              <Link to="/countries" className="btn btn-primary">
                Управление
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;