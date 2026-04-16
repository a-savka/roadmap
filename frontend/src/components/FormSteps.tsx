import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getFormSteps, updateFormStep } from '../services/apiService';
import { useAppConfigContext } from '../context/AppConfigContext';
import type { FormStep } from '../types';

const FormSteps = ({ formId }: { formId: string | number }) => {
  const queryClient = useQueryClient();
  const { t } = useAppConfigContext();

  const { data: steps = [], isLoading, error } = useQuery<FormStep[] | null>({
    queryKey: ['formSteps', formId],
    queryFn: () => getFormSteps(formId),
  });

  const updateStepMutation = useMutation({
    mutationFn: updateFormStep,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['formSteps', formId] });
    },
    onError: (error) => {
      console.error('Failed to update step:', error);
    },
  });

  const handleCheckboxChange = (stepName: string, currentCompleted: number) => {
    updateStepMutation.mutate({
      formId: typeof formId === 'string' ? parseInt(formId, 10) : formId,
      stepName: stepName,
      completed: currentCompleted ? 0 : 1,
    });
  };

  if (!steps) {
    return null;
  }

  if (isLoading) {
    return <div>{t('form.steps.loading')}</div>;
  }

  if (error) {
    return <div className="alert alert-danger">{t('form.steps.error')}: {error.message}</div>;
  }

  const sortedSteps = [...steps].sort((a, b) => a.step.stepOrder - b.step.stepOrder);
  const allStepsCompleted = sortedSteps.length > 0 && sortedSteps.every((step: FormStep) => step.completed === 1);

  return (
    <div className="mt-4">
      <h5 className="mb-3">{t('form.steps.title')}</h5>
      <div className="list-group">
        {sortedSteps.map((formStep: FormStep) => (
          <label key={formStep.step.stepName} className="list-group-item d-flex align-items-center">
            <input
              className="form-check-input me-3"
              type="checkbox"
              checked={formStep.completed === 1}
              onChange={() => handleCheckboxChange(formStep.step.stepName, formStep.completed)}
              disabled={updateStepMutation.isPending}
            />
            {formStep.step.stepDescription}
            {formStep.step.deadlineDays && (
              <span className="ms-2 text-muted">(до {formStep.step.deadlineDays} дней)</span>
            )}
          </label>
        ))}
      </div>

      {allStepsCompleted && (
        <div className="alert alert-success mt-4">
          {t('form.steps.all.completed.message')}
        </div>
      )}
    </div>
  );
};

export default FormSteps;
