import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getFormSteps, updateFormStep } from '../services/apiService';
import type { FormStep } from '../types';

const FormSteps = ({ formId }: { formId: string | number }) => {
  const queryClient = useQueryClient();

  const { data: steps = [], isLoading, error } = useQuery<FormStep[] | null>({
    queryKey: ['formSteps', formId],
    queryFn: () => getFormSteps(formId),
  });

  const updateStepMutation = useMutation({
    mutationFn: updateFormStep,
    onSuccess: () => {
      // Invalidate and refetch the steps query to get the updated list
      queryClient.invalidateQueries({ queryKey: ['formSteps', formId] });
    },
    onError: (error) => {
      // It's good practice to handle errors, e.g., show a notification
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
    return <div>Загрузка шагов...</div>;
  }

  if (error) {
    return <div className="alert alert-danger">Не удалось загрузить шаги: {error.message}</div>;
  }

  const allStepsCompleted = steps.length > 0 && steps.every((step: FormStep) => step.completed === 1);

  return (
    <div className="mt-4">
      <h5 className="mb-3">Дальнейшие шаги</h5>
      <div className="list-group">
        {steps.map((formStep: FormStep) => (
          <label key={formStep.step.stepName} className="list-group-item d-flex align-items-center">
            <input
              className="form-check-input me-3"
              type="checkbox"
              checked={formStep.completed === 1}
              onChange={() => handleCheckboxChange(formStep.step.stepName, formStep.completed)}
              disabled={updateStepMutation.isPending}
            />
            {formStep.step.stepDescription}
          </label>
        ))}
      </div>

      {allStepsCompleted && (
        <div className="alert alert-success mt-4">
          Все предварительные условия выполнены. Вы можете приступить к подаче заявления.
        </div>
      )}
    </div>
  );
};

export default FormSteps;
