import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getAppMessage, createAppMessage, updateAppMessage } from '../services/api';

const MessageFormPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEdit = id && id !== 'new';
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    id: 0,
    messageKey: '',
    messageText: '',
    messageTextEn: null as string | null,
    category: null as string | null,
  });

  const { data: message, isLoading } = useQuery({
    queryKey: ['message', id],
    queryFn: () => getAppMessage(parseInt(id!)),
    enabled: !!isEdit && !!id,
  });

  useEffect(() => {
    if (message && isEdit) {
      setFormData(message);
    }
  }, [message, isEdit]);

  const createMutation = useMutation({
    mutationFn: createAppMessage,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['messages'] });
      navigate('/messages');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data: typeof formData) => updateAppMessage(parseInt(id!), data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['messages'] });
      navigate('/messages');
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
      <h2 className="mb-4">{isEdit ? 'Редактирование сообщения' : 'Создание сообщения'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Ключ сообщения</label>
          <input
            type="text"
            className="form-control"
            value={formData.messageKey}
            onChange={(e) => setFormData({ ...formData, messageKey: e.target.value })}
            required
            disabled={isEdit}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Текст (RU)</label>
          <textarea
            className="form-control"
            value={formData.messageText}
            onChange={(e) => setFormData({ ...formData, messageText: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Текст (EN)</label>
          <textarea
            className="form-control"
            value={formData.messageTextEn || ''}
            onChange={(e) =>
              setFormData({ ...formData, messageTextEn: e.target.value || null })
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Категория</label>
          <input
            type="text"
            className="form-control"
            value={formData.category || ''}
            onChange={(e) =>
              setFormData({ ...formData, category: e.target.value || null })
            }
          />
        </div>
        <button type="submit" className="btn btn-primary me-2">
          {isEdit ? 'Сохранить' : 'Создать'}
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => navigate('/messages')}
        >
          Отмена
        </button>
      </form>
    </div>
  );
};

export default MessageFormPage;