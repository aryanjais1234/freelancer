import type { AuthState } from './state';

export const getters = {
  isAuthenticated: (state: AuthState) => !!state.token,
  currentUser: (state: AuthState) => state.UIfromOriginal,
  userRole: (state: AuthState) => state.UIfromOriginal?.role || null,
  token: (state: AuthState) => state.token,
  isLoading: (state: AuthState) => state.isLoading,
  error: (state: AuthState) => state.error,
  isClient: (state: AuthState) => state.UIfromOriginal?.role === 'CLIENT',
  isFreelancer: (state: AuthState) => state.UIfromOriginal?.role === 'FREELANCER',
};
