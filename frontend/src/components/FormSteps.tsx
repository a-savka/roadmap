import { useState } from 'react';
import { useAppConfigContext } from '../context/AppConfigContext';
import type { Step } from '../types';

const FormSteps = ({ steps, entryDate }: { steps: Step[]; entryDate?: string }) => {
  const { t } = useAppConfigContext();
  const [completedSteps, setCompletedSteps] = useState<Set<string>>(new Set());

  const sortedSteps = [...steps].sort((a, b) => a.stepOrder - b.stepOrder);

  const handleCheckboxChange = (stepName: string) => {
    setCompletedSteps((prev) => {
      const next = new Set(prev);
      if (next.has(stepName)) {
        next.delete(stepName);
      } else {
        next.add(stepName);
      }
      return next;
    });
  };

  const allStepsCompleted = sortedSteps.length > 0 && sortedSteps.every((step) => completedSteps.has(step.stepName));

  const getDeadlineInfo = (step: Step) => {
    const deadlineDays = step.deadlineDays;
    if (!entryDate || !deadlineDays || deadlineDays <= 0) return null;

    const entry = new Date(entryDate);
    const deadline = new Date(entry.getTime() + deadlineDays * 86400000);
    const now = new Date();
    const isOverdue = deadline < now;
    const overdueDays = Math.floor((now.getTime() - deadline.getTime()) / 86400000);

    return { deadline, isOverdue, overdueDays };
  };

  return (
    <div className="mt-4">
      <h5 className="mb-3">{t('form.steps.title')}</h5>
      {sortedSteps.length === 0 ? (
        <p className="text-muted">{t('form.steps.loading')}</p>
      ) : (
        <div className="list-group">
          {sortedSteps.map((step) => {
            const deadlineInfo = getDeadlineInfo(step);
            const isOverdue = deadlineInfo?.isOverdue ?? false;

            return (
              <label
                key={step.stepName}
                className={`list-group-item d-flex align-items-center ${isOverdue ? 'bg-danger-subtle' : ''}`}
              >
                <input
                  className="form-check-input me-3"
                  type="checkbox"
                  checked={completedSteps.has(step.stepName)}
                  onChange={() => handleCheckboxChange(step.stepName)}
                  disabled={isOverdue}
                />
                <span className={isOverdue ? 'text-danger fw-bold' : ''}>
                  {step.stepDescription}
                </span>
                {isOverdue ? (
                  <span className="ms-2 text-danger fw-bold">
                    (просрочено на {deadlineInfo!.overdueDays} дн.)
                  </span>
                ) : deadlineInfo ? (
                  <span className="ms-2 text-muted">
                    (до {deadlineInfo.deadline.toLocaleDateString('ru-RU')})
                  </span>
                ) : null}
              </label>
            );
          })}
        </div>
      )}

      {allStepsCompleted && (
        <div className="alert alert-success mt-4">
          {t('form.steps.all.completed.message')}
        </div>
      )}
    </div>
  );
};

export default FormSteps;