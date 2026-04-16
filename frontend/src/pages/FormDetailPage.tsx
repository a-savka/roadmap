import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { validateForm } from '../services/apiService';
import FormSteps from '../components/FormSteps';
import type { ValidationResult } from '../types';

const FormDetailPage = () => {
  const { formId } = useParams();

  const { data: validationData, isLoading, error } = useQuery<ValidationResult | null>({
    queryKey: ['formValidation', formId],
    queryFn: () => validateForm(formId!),
    enabled: !!formId,
  });

  if (isLoading) {
    return <div className="text-center">Проверка формы...</div>;
  }

  if (error) {
    return <div className="alert alert-danger">Ошибка при проверке формы: {error.message}</div>;
  }

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <div className="bg-light p-4 border rounded" style={{ width: '600px' }}>
        <h2 className="text-center mb-4">Результат проверки анкеты</h2>
        {validationData && (
          <div>
            {validationData.overdue ? (
              <p className="alert alert-warning">
                Срок подачи заявления просрочен на {validationData.overdueDays} дней.<br/>
                Вам необходимо выехать из страны.
              </p>
            ) : (
              <div>
                <p className="alert alert-success">
                    Условия для подачи заявления выполнены.
                </p>
                <FormSteps formId={formId!} />
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default FormDetailPage;
