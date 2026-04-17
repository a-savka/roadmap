import type {
  AdminCredentials,
  AdminAuthResponse,
  Country,
  Step,
  StepCreateDto,
  StepUpdateDto,
  StepCondition,
  ValidationRule,
  AppMessage,
} from '../types';

const API_BASE = '/api';

let adminToken: string | null = null;

export const setAdminToken = (token: string | null) => {
  adminToken = token;
};

const apiFetch = async <T>(url: string, options?: RequestInit): Promise<T | null> => {
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
  };
  if (adminToken) {
    headers['X-Admin-Token'] = adminToken;
  }

  const response = await fetch(`${API_BASE}${url}`, {
    ...options,
    headers: {
      ...headers,
      ...options?.headers,
    },
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Unknown error' }));
    throw new Error(error.message || `HTTP ${response.status}`);
  }

  if (response.status === 204) return null;

  const contentType = response.headers.get('content-type');
  if (contentType?.includes('application/json')) {
    return response.json();
  }
  return null;
};

// Auth
export const adminLogin = (credentials: AdminCredentials) =>
  apiFetch<AdminAuthResponse>('/admin/auth/login', {
    method: 'POST',
    body: JSON.stringify(credentials),
  });

// Steps
export const getSteps = () => apiFetch<Step[]>('/admin/steps');
export const getStep = (stepName: string) => apiFetch<Step>(`/admin/steps/${stepName}`);
export const createStep = (data: StepCreateDto) =>
  apiFetch<Step>('/admin/steps', { method: 'POST', body: JSON.stringify(data) });
export const updateStep = (stepName: string, data: StepUpdateDto) =>
  apiFetch<Step>(`/admin/steps/${stepName}`, { method: 'PUT', body: JSON.stringify(data) });
export const deleteStep = (stepName: string) =>
  apiFetch(`/admin/steps/${stepName}`, { method: 'DELETE' });

// Step Conditions
export const getStepConditions = (stepName: string) =>
  apiFetch<StepCondition[]>(`/admin/steps/${stepName}/conditions`);
export const createStepCondition = (stepName: string, data: Omit<StepCondition, 'id'>) =>
  apiFetch<StepCondition>(`/admin/steps/${stepName}/conditions`, {
    method: 'POST',
    body: JSON.stringify({ ...data, stepName }),
  });
export const updateStepCondition = (stepName: string, id: number, data: StepCondition) =>
  apiFetch<StepCondition>(`/admin/steps/${stepName}/conditions/${id}`, {
    method: 'PUT',
    body: JSON.stringify(data),
  });
export const deleteStepCondition = (stepName: string, id: number) =>
  apiFetch(`/admin/steps/${stepName}/conditions/${id}`, { method: 'DELETE' });

// Validation Rules
export const getValidationRules = () => apiFetch<ValidationRule[]>('/admin/validation-rules');
export const getValidationRule = (id: number) => apiFetch<ValidationRule>(`/admin/validation-rules/${id}`);
export const createValidationRule = (data: ValidationRule) =>
  apiFetch<ValidationRule>('/admin/validation-rules', { method: 'POST', body: JSON.stringify(data) });
export const updateValidationRule = (id: number, data: ValidationRule) =>
  apiFetch<ValidationRule>(`/admin/validation-rules/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const deleteValidationRule = (id: number) =>
  apiFetch(`/admin/validation-rules/${id}`, { method: 'DELETE' });

// App Messages
export const getAppMessages = () => apiFetch<AppMessage[]>('/admin/messages');
export const getAppMessage = (id: number) => apiFetch<AppMessage>(`/admin/messages/${id}`);
export const createAppMessage = (data: AppMessage) =>
  apiFetch<AppMessage>('/admin/messages', { method: 'POST', body: JSON.stringify(data) });
export const updateAppMessage = (id: number, data: AppMessage) =>
  apiFetch<AppMessage>(`/admin/messages/${id}`, { method: 'PUT', body: JSON.stringify(data) });
export const deleteAppMessage = (id: number) =>
  apiFetch(`/admin/messages/${id}`, { method: 'DELETE' });

// Countries
export const getCountries = () => apiFetch<Country[]>('/admin/countries');
export const getCountry = (code: string) => apiFetch<Country>(`/admin/countries/${code}`);
export const createCountry = (data: { code: string; name: string }) =>
  apiFetch<Country>('/admin/countries', { method: 'POST', body: JSON.stringify(data) });
export const updateCountry = (code: string, data: { name: string }) =>
  apiFetch<Country>(`/admin/countries/${code}`, { method: 'PUT', body: JSON.stringify(data) });
export const deleteCountry = (code: string) =>
  apiFetch(`/admin/countries/${code}`, { method: 'DELETE' });