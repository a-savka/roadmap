import { createContext, useContext } from 'react';
import type { ReactNode } from 'react';
import { useAppConfig } from '../hooks/useAppConfig';
import type { AppConfig } from '../types';

interface AppConfigContextType {
  config: AppConfig | null;
  isLoading: boolean;
  error: Error | null;
  t: (key: string) => string;
}

const AppConfigContext = createContext<AppConfigContextType | undefined>(undefined);

interface AppConfigProviderProps {
  children: ReactNode;
}

export const AppConfigProvider = ({ children }: AppConfigProviderProps) => {
  const { config, isLoading, error, t } = useAppConfig();

  return (
    <AppConfigContext.Provider value={{ config, isLoading, error, t }}>
      {children}
    </AppConfigContext.Provider>
  );
};

export const useAppConfigContext = (): AppConfigContextType => {
  const context = useContext(AppConfigContext);
  if (context === undefined) {
    throw new Error('useAppConfigContext must be used within an AppConfigProvider');
  }
  return context;
};
