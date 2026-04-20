import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Link, useNavigate } from 'react-router-dom';
import { getAppMessages, deleteAppMessage } from '../services/api';

const MessagesListPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: messages = [], isLoading } = useQuery({
    queryKey: ['messages'],
    queryFn: getAppMessages,
  });

  const deleteMutation = useMutation({
    mutationFn: deleteAppMessage,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['messages'] });
    },
  });

  const handleDelete = (id: number) => {
    if (confirm('Удалить сообщение?')) {
      deleteMutation.mutate(id);
    }
  };

  if (isLoading) return <div>Загрузка...</div>;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Сообщения</h2>
        <Link to="/messages/new" className="btn btn-success">
          Добавить сообщение
        </Link>
      </div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Ключ</th>
            <th>Текст (RU)</th>
            <th>Текст (EN)</th>
            <th>Категория</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {messages.map((msg) => (
            <tr key={msg.id}>
              <td>{msg.id}</td>
              <td>{msg.messageKey}</td>
              <td>{msg.messageText}</td>
              <td>{msg.messageTextEn ?? '-'}</td>
              <td>{msg.category ?? '-'}</td>
              <td>
                <button
                  className="btn btn-sm btn-primary me-2"
                  onClick={() => navigate(`/messages/${msg.id}`)}
                >
                  ✏️
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(msg.id)}
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

export default MessagesListPage;