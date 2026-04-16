import { useState, useEffect } from 'react';
import type { ChangeEvent, FormEvent } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import useUserStore from '../store/userStore';
import { getCountries, getUserForm, saveUserForm, updateUserForm } from '../services/apiService';
import { useAppConfigContext } from '../context/AppConfigContext';
import { useNavigate } from 'react-router-dom';
import type { Country, Form } from '../types';

const FormPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const user = useUserStore((state) => state.user);
  const { t } = useAppConfigContext();
  const [formData, setFormData] = useState({
    entryDate: '',
    citizenshipCountryCode: '',
    visitPurpose: 'work',
    relocationProgramStatus: 'no',
    hqsStatus: 'no',
  });
  const [error, setError] = useState('');

  useEffect(() => {
    if (!user) {
      navigate('/login');
    }
  }, [user, navigate]);

  const { data: countries = [] } = useQuery<Country[] | null, Error>({
    queryKey: ['countries'],
    queryFn: getCountries,
  });

  const { data: existingForm } = useQuery<Form | null, Error>({
    queryKey: ['form', user?.username],
    queryFn: () => getUserForm(user!.username),
    enabled: !!user,
  });

  const isEditMode = !!existingForm;

  useEffect(() => {
    if (existingForm) {
      setFormData({
        entryDate: existingForm.entryDate || '',
        citizenshipCountryCode: existingForm.citizenshipCountry?.code || '',
        visitPurpose: existingForm.visitPurpose || 'work',
        relocationProgramStatus: existingForm.relocationProgramStatus || 'no',
        hqsStatus: existingForm.hqsStatus || 'no',
      });
    }
  }, [existingForm]);

  const createMutation = useMutation({
    mutationFn: (data: Record<string, unknown>) => saveUserForm(data),
    onSuccess: (data) => {
      console.log('Form saved successfully:', data);
      queryClient.invalidateQueries({ queryKey: ['form', user?.username] });
    },
    onError: (error: Error) => {
      setError(error.message);
    },
  });

  const updateMutation = useMutation({
    mutationFn: (vars: { formId: number; formData: Record<string, unknown> }) => updateUserForm(vars.formId, vars.formData),
    onSuccess: (data) => {
      console.log('Form updated successfully:', data);
      queryClient.invalidateQueries({ queryKey: ['form', user?.username] });
    },
    onError: (error: Error) => {
      setError(error.message);
    },
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    setError('');
    const payload = { ...formData, username: user!.username };
    if (isEditMode) {
      updateMutation.mutate({ formId: existingForm.id, formData: payload });
    } else {
      createMutation.mutate(payload);
    }
  };

  if (!user) {
    return null;
  }

  const isPending = createMutation.isPending || updateMutation.isPending;

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <div className="bg-light p-4 border rounded" style={{ width: '600px' }}>
        <h2 className="text-center mb-4">{t('form.page.title')}</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">{t('form.entry.date.label')}</label>
            <input
              type="date"
              name="entryDate"
              className="form-control"
              value={formData.entryDate}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">{t('form.country.label')}</label>
            <select
              name="citizenshipCountryCode"
              className="form-select"
              value={formData.citizenshipCountryCode}
              onChange={handleChange}
            >
              <option value="">{t('form.select.country.placeholder')}</option>
              {(countries ?? []).map((country) => (
                <option key={country.code} value={country.code}>
                  {country.name}
                </option>
              ))}
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label">{t('form.purpose.label')}</label>
            <select
              name="visitPurpose"
              className="form-select"
              value={formData.visitPurpose}
              onChange={handleChange}
            >
              <option value="work">{t('purpose.work')}</option>
              <option value="recreation">{t('purpose.recreation')}</option>
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label">{t('form.relocation.label')}</label>
            <select
              name="relocationProgramStatus"
              className="form-select"
              value={formData.relocationProgramStatus}
              onChange={handleChange}
            >
              <option value="yes">{t('status.yes')}</option>
              <option value="no">{t('status.no')}</option>
              <option value="family">{t('status.family')}</option>
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label">{t('form.hqs.label')}</label>
            <select
              name="hqsStatus"
              className="form-select"
              value={formData.hqsStatus}
              onChange={handleChange}
            >
              <option value="yes">{t('status.yes')}</option>
              <option value="no">{t('status.no')}</option>
              <option value="family">{t('status.family')}</option>
            </select>
          </div>

          {error && <div className="alert alert-danger">{error}</div>}
          <button type="submit" className="btn btn-primary w-100" disabled={isPending}>
            {isPending ? t('form.save.pending') : (isEditMode ? t('form.update.button') : t('form.save.button'))}
          </button>
          {isEditMode && (
            <button
              type="button"
              className="btn btn-secondary w-100 mt-2"
              onClick={() => navigate(`/form-details/${existingForm.id}`)}
            >
              {t('form.view.button')}
            </button>
          )}
        </form>
      </div>
    </div>
  );
};

export default FormPage;
