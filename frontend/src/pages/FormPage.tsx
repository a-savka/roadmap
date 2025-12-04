import { useState, useEffect } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import useUserStore from '../store/userStore';
import { getCountries, getUserForm, saveUserForm, updateUserForm } from '../services/apiService';
import { useNavigate } from 'react-router-dom';

const visitPurposeOptions = {
  work: 'Работа',
  recreation: 'Отдых',
};

const statusOptions = {
  yes: 'Да',
  no: 'Нет',
  family: 'Член семьи',
};

const FormPage = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const user = useUserStore((state) => state.user);
  const [formData, setFormData] = useState({
    entryDate: '',
    citizenshipCountryCode: '',
    visitPurpose: 'work',
    relocationProgramStatus: 'no',
    hqsStatus: 'no',
  });
  const [error, setError] = useState('');

  // Redirect if not logged in
  useEffect(() => {
    if (!user) {
      navigate('/login');
    }
  }, [user, navigate]);

  const { data: countries = [] } = useQuery({
    queryKey: ['countries'],
    queryFn: getCountries,
  });

  const { data: existingForm } = useQuery({
    queryKey: ['form', user?.username],
    queryFn: () => getUserForm(user.username),
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
    mutationFn: saveUserForm,
    onSuccess: (data) => {
      console.log('Form saved successfully:', data);
      queryClient.invalidateQueries({ queryKey: ['form', user?.username] });
    },
    onError: (error) => {
      setError(error.message);
    },
  });

  const updateMutation = useMutation({
    mutationFn: (vars) => updateUserForm(vars.formId, vars.formData),
    onSuccess: (data) => {
      console.log('Form updated successfully:', data);
      queryClient.invalidateQueries({ queryKey: ['form', user?.username] });
    },
    onError: (error) => {
      setError(error.message);
    },
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');
    const payload = { ...formData, username: user.username };
    if (isEditMode) {
      updateMutation.mutate({ formId: existingForm.id, formData: payload });
    } else {
      createMutation.mutate(payload);
    }
  };

  if (!user) {
    return null; // Or a loading spinner
  }

  const isPending = createMutation.isPending || updateMutation.isPending;

  return (
    <div className="d-flex justify-content-center align-items-center w-100">
      <div className="bg-light p-4 border rounded" style={{ width: '600px' }}>
        <h2 className="text-center mb-4">Анкета</h2>
        <form onSubmit={handleSubmit}>
          {/* Entry Date */}
          <div className="mb-3">
            <label className="form-label">Дата въезда</label>
            <input
              type="date"
              name="entryDate"
              className="form-control"
              value={formData.entryDate}
              onChange={handleChange}
            />
          </div>

          {/* Citizenship */}
          <div className="mb-3">
            <label className="form-label">Страна гражданства</label>
            <select
              name="citizenshipCountryCode"
              className="form-select"
              value={formData.citizenshipCountryCode}
              onChange={handleChange}
            >
              <option value="">Выберите страну</option>
              {countries.map((country) => (
                <option key={country.code} value={country.code}>
                  {country.name}
                </option>
              ))}
            </select>
          </div>

          {/* Visit Purpose */}
          <div className="mb-3">
            <label className="form-label">Цель визита</label>
            <select
              name="visitPurpose"
              className="form-select"
              value={formData.visitPurpose}
              onChange={handleChange}
            >
              {Object.entries(visitPurposeOptions).map(([value, text]) => (
                <option key={value} value={value}>{text}</option>
              ))}
            </select>
          </div>

          {/* Relocation Program */}
          <div className="mb-3">
            <label className="form-label">Статус: «Программа переселения»</label>
            <select
              name="relocationProgramStatus"
              className="form-select"
              value={formData.relocationProgramStatus}
              onChange={handleChange}
            >
              {Object.entries(statusOptions).map(([value, text]) => (
                <option key={value} value={value}>{text}</option>
              ))}
            </select>
          </div>

          {/* HQS Status */}
          <div className="mb-3">
            <label className="form-label">Статус: «Высококвалифицированный специалист»</label>
            <select
              name="hqsStatus"
              className="form-select"
              value={formData.hqsStatus}
              onChange={handleChange}
            >
              {Object.entries(statusOptions).map(([value, text]) => (
                <option key={value} value={value}>{text}</option>
              ))}
            </select>
          </div>

          {error && <div className="alert alert-danger">{error}</div>}
          <button type="submit" className="btn btn-primary w-100" disabled={isPending}>
            {isPending ? 'Сохранение...' : (isEditMode ? 'Обновить анкету' : 'Сохранить анкету')}
          </button>
          {isEditMode && (
            <button type="button" className="btn btn-secondary w-100 mt-2">
              Просмотр анкеты
            </button>
          )}
        </form>
      </div>
    </div>
  );
};

export default FormPage;
