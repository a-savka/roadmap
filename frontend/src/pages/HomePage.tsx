import { useNavigate } from 'react-router-dom';
import { useAppConfigContext } from '../context/AppConfigContext';

const HomePage = () => {
  const navigate = useNavigate();
  const { t } = useAppConfigContext();

  const handleClick = () => {
    navigate('/login');
  };

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <button className="btn btn-primary btn-lg" onClick={handleClick}>
        {t('home.page.button.text')}
      </button>
    </div>
  );
};

export default HomePage;