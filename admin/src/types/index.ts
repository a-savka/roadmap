export interface AdminCredentials {
  username: string;
  password: string;
}

export interface AdminAuthResponse {
  username: string;
  role: string;
  token: string;
}

export interface Country {
  code: string;
  name: string;
}

export interface StepCondition {
  id?: number;
  stepName: string;
  countryCode: string | null;
  visitPurpose: string | null;
  relocationProgramStatus: string | null;
  hqsStatus: string | null;
}

export interface Step {
  stepName: string;
  stepDescription: string;
  stepOrder: number;
  enabled: boolean;
  deadlineDays: number | null;
  conditions: StepCondition[];
}

export interface StepCreateDto {
  stepName: string;
  stepDescription: string;
  stepOrder: number;
  enabled: boolean;
  deadlineDays: number | null;
}

export interface StepUpdateDto {
  stepDescription: string;
  stepOrder: number;
  enabled: boolean;
  deadlineDays: number | null;
}

export interface ValidationRule {
  id: number;
  countryCode: string | null;
  visitPurpose: string | null;
  relocationProgramStatus: string | null;
  hqsStatus: string | null;
  maxDays: number;
}

export interface AppMessage {
  id: number;
  messageKey: string;
  messageText: string;
  messageTextEn: string | null;
  category: string | null;
}

export interface AppConfig {
  countries: Country[];
  steps: Step[];
  validationRules: ValidationRule[];
  messages: Record<string, string>;
}