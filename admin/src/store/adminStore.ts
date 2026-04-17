import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { setAdminToken } from '../services/api';

interface AdminState {
  isAuthenticated: boolean;
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
}

const useAdminStore = create<AdminState>()(
  persist(
    (set) => ({
      isAuthenticated: false,
      token: null,
      login: (token) => {
        setAdminToken(token);
        set({ isAuthenticated: true, token });
      },
      logout: () => {
        setAdminToken(null);
        set({ isAuthenticated: false, token: null });
      },
    }),
    { name: 'admin-auth', onRehydrateStorage: () => (state) => {
      if (state?.token) {
        setAdminToken(state.token);
      }
    } }
  )
);

export default useAdminStore;