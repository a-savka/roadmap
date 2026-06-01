import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { validateForm, getFormSteps } from '../services/apiService';
import { useAppConfigContext } from '../context/AppConfigContext';
import FormSteps from '../components/FormSteps';
import type { ValidationResult, Step } from '../types';

const FormDetailPage = () => {
  const { formId } = useParams();
  const { t } = useAppConfigContext();

  const { data: validationData, isLoading, error } = useQuery<ValidationResult | null>({
    queryKey: ['formValidation', formId],
    queryFn: () => validateForm(formId!),
    enabled: !!formId,
  });

  const { data: steps = [] } = useQuery<Step[] | null>({
    queryKey: ['formSteps', formId],
    queryFn: () => getFormSteps(formId!),
    enabled: !!formId && !validationData?.overdue,
  });

  if (isLoading) {
    return <div className="text-center">{t('form.validation.checking')}</div>;
  }

  if (error) {
    return <div className="alert alert-danger">{t('form.validation.error')}: {error.message}</div>;
  }

  const overdueMessage = t('form.validation.overdue.message').replace('{days}', String(validationData?.overdueDays ?? 0));

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <div className="bg-light p-4 border rounded" style={{ width: '800px' }}>
        <h2 className="text-center mb-4">{t('form.detail.page.title')}</h2>
        {validationData && (
          <div>
            {validationData.overdue ? (
              <p className="alert alert-warning">{overdueMessage}</p>
            ) : (
              <div>
                <p className="alert alert-success">{t('form.validation.ok.message')}</p>
                <FormSteps steps={steps ?? []} />
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default FormDetailPage;
