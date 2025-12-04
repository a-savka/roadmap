import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/login');
  };

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <button className="btn btn-primary btn-lg" onClick={handleClick}>
        Получить дорожную карту
      </button>
    </div>
  );
};

export default HomePage;