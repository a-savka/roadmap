import { getFullConfig, getMessages, getSteps } from './apiService';
import type { AppConfig, AppMessage, Step } from '../types';

export const loadConfig = async (): Promise<AppConfig | null> => {
  return getFullConfig();
};

export const loadMessages = async (): Promise<Record<string, string>> => {
  const messages = await getMessages();
  if (!messages) return {};
  return messages.reduce((acc, msg: AppMessage) => {
    acc[msg.messageKey] = msg.messageText;
    return acc;
  }, {} as Record<string, string>);
};

export const loadSteps = async (): Promise<Step[]> => {
  const steps = await getSteps();
  return steps ?? [];
};
