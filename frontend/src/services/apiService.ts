import { QueryClient } from '@tanstack/react-query';
import type {
  Country,
  Form,
  Step,
  ValidationResult,
  AppConfig,
  AppMessage,
  ValidationRule
} from '../types';

const API_BASE_URL = '/api';

export const queryClient = new QueryClient();

const apiFetch = async <T>(url: string, options?: RequestInit): Promise<T | null> => {
  const response = await fetch(`${API_BASE_URL}${url}`, options);
  if (!response.ok) {
    if (response.status === 404) {
      return null;
    }
    const errorData = await response.json().catch(() => ({ message: 'An unknown error occurred' }));
    throw new Error(errorData.message || 'API request failed');
  }
  const contentType = response.headers.get('content-type');
  if (contentType && contentType.indexOf('application/json') !== -1) {
    return response.json();
  }
  return null;
};

export const getCountries = (): Promise<Country[] | null> => 
  apiFetch<Country[]>('/countries');

export const getUserForm = (username: string): Promise<Form | null> => 
  apiFetch<Form>(`/forms/${username}`);

export const saveUserForm = (formData: Record<string, unknown>): Promise<Form | null> => {
  return apiFetch<Form>('/forms', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(formData),
  });
};

export const updateUserForm = (formId: number, formData: Record<string, unknown>): Promise<Form | null> => {
  return apiFetch<Form>(`/forms/${formId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(formData),
  });
};

export const validateForm = (formId: string | number): Promise<ValidationResult | null> => 
  apiFetch<ValidationResult>(`/forms/${formId}/validate`);

export const getFormSteps = (formId: string | number): Promise<Step[] | null> => 
  apiFetch<Step[]>(`/forms/${formId}/steps`);

export const getSteps = (): Promise<Step[] | null> => 
  apiFetch<Step[]>('/steps');

export const getValidationRules = (): Promise<ValidationRule[] | null> => 
  apiFetch<ValidationRule[]>('/validation-rules');

export const getMessages = (): Promise<AppMessage[] | null> => 
  apiFetch<AppMessage[]>('/messages');

export const getFullConfig = (): Promise<AppConfig | null> => 
  apiFetch<AppConfig>('/config');
