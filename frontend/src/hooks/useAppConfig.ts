import { useState, useEffect, useCallback } from 'react';
import { loadConfig } from '../services/configService';
import type { AppConfig } from '../types';

interface UseAppConfigReturn {
  config: AppConfig | null;
  isLoading: boolean;
  error: Error | null;
  t: (key: string) => string;
}

export const useAppConfig = (): UseAppConfigReturn => {
  const [config, setConfig] = useState<AppConfig | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchConfig = async () => {
      try {
        setIsLoading(true);
        const data = await loadConfig();
        setConfig(data);
        setError(null);
      } catch (err) {
        setError(err instanceof Error ? err : new Error('Failed to load config'));
      } finally {
        setIsLoading(false);
      }
    };

    fetchConfig();
  }, []);

  const t = useCallback((key: string): string => {
    if (!config?.messages) return key;
    return config.messages[key] ?? key;
  }, [config]);

  return { config, isLoading, error, t };
};
