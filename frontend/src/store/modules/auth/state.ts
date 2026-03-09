import type { UserBackend, UserUI, UserModified } from '../../../types/user.types';

export interface AuthState {
  originalFromBackend: UserBackend | null;
  UIfromOriginal: UserUI | null;
  modifiedToBackend: UserModified | null;
  token: string | null;
  isLoading: boolean;
  error: string | null;
}

export const state = (): AuthState => ({
  originalFromBackend: null,
  UIfromOriginal: null,
  modifiedToBackend: null,
  token: localStorage.getItem('token'),
  isLoading: false,
  error: null,
});
