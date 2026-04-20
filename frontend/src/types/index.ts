export interface User {
  username: string;
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

export interface FormStep {
  formId: number;
  stepName: string;
  completed: number;
  stepDescription: string;
  stepOrder: number;
  enabled: boolean;
  deadlineDays: number | null;
  deadlineDate: string | null;
  overdue: boolean;
  overdueDays: number;
}

export interface Form {
  id: number;
  user: { username: string };
  entryDate: string;
  citizenshipCountry: { code: string; name: string };
  visitPurpose: string;
  relocationProgramStatus: string;
  hqsStatus: string;
}

export interface ValidationResult {
  overdue: boolean;
  overdueDays: number;
  applicableSteps?: Step[];
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
