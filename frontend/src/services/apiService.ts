import { QueryClient } from '@tanstack/react-query';

const API_BASE_URL = '/api';

export const queryClient = new QueryClient();

// Generic fetch function
const apiFetch = async (url, options = {}) => {
  const response = await fetch(`${API_BASE_URL}${url}`, options);
  if (!response.ok) {
    if (response.status === 404) {
      return null; // Handle 404 gracefully, e.g., for forms not found
    }
    const errorData = await response.json().catch(() => ({ message: 'An unknown error occurred' }));
    throw new Error(errorData.message || 'API request failed');
  }
  // Handle responses with no content
  const contentType = response.headers.get('content-type');
  if (contentType && contentType.indexOf('application/json') !== -1) {
    return response.json();
  }
  return null;
};

// Country API
export const getCountries = () => apiFetch('/countries');

// Form API
export const getUserForm = (username) => apiFetch(`/forms/${username}`);

export const saveUserForm = (formData) => {
  return apiFetch('/forms', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formData),
  });
};

export const updateUserForm = (formId, formData) => {
  return apiFetch(`/forms/${formId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formData),
  });
};

export const validateForm = (formId) => apiFetch(`/forms/${formId}/validate`);

// Form Steps API
export const getFormSteps = (formId) => apiFetch(`/form-steps/${formId}`);

export const updateFormStep = (stepData) => {
    return apiFetch('/form-steps', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(stepData),
    });
};

