import { Routes, Route } from 'react-router-dom';
import DashboardPage from './DashboardPage';
import StepsListPage from './StepsListPage';
import StepFormPage from './StepFormPage';
import StepConditionsPage from './StepConditionsPage';
import CountriesListPage from './CountriesListPage';
import CountryFormPage from './CountryFormPage';
import ValidationRulesListPage from './ValidationRulesListPage';
import ValidationRuleFormPage from './ValidationRuleFormPage';
import MessagesListPage from './MessagesListPage';
import MessageFormPage from './MessageFormPage';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<DashboardPage />} />
      <Route path="/steps" element={<StepsListPage />} />
      <Route path="/steps/new" element={<StepFormPage />} />
      <Route path="/steps/:stepName" element={<StepFormPage />} />
      <Route path="/steps/:stepName/conditions" element={<StepConditionsPage />} />
      <Route path="/countries" element={<CountriesListPage />} />
      <Route path="/countries/new" element={<CountryFormPage />} />
      <Route path="/countries/:code" element={<CountryFormPage />} />
      <Route path="/validation-rules" element={<ValidationRulesListPage />} />
      <Route path="/validation-rules/new" element={<ValidationRuleFormPage />} />
      <Route path="/validation-rules/:id" element={<ValidationRuleFormPage />} />
      <Route path="/messages" element={<MessagesListPage />} />
      <Route path="/messages/new" element={<MessageFormPage />} />
      <Route path="/messages/:id" element={<MessageFormPage />} />
    </Routes>
  );
};

export default AppRoutes;