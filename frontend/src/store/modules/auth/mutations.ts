import type { AuthState } from './state';
import { AUTH_MUTATIONS } from './mutation-types';
import type { UserBackend, UserUI, UserModified } from '../../../types/user.types';

export const mutations = {
  [AUTH_MUTATIONS.SET_LOADING](state: AuthState, loading: boolean) {
    state.isLoading = loading;
  },
  [AUTH_MUTATIONS.SET_ERROR](state: AuthState, error: string | null) {
    state.error = error;
  },
  [AUTH_MUTATIONS.SET_TOKEN](state: AuthState, token: string | null) {
    state.token = token;
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
  },
  [AUTH_MUTATIONS.SET_USER_ORIGINAL](state: AuthState, user: UserBackend | null) {
    state.originalFromBackend = user;
  },
  [AUTH_MUTATIONS.SET_USER_UI](state: AuthState, user: UserUI | null) {
    state.UIfromOriginal = user;
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  },
  [AUTH_MUTATIONS.SET_USER_MODIFIED](state: AuthState, user: UserModified | null) {
    state.modifiedToBackend = user;
  },
  [AUTH_MUTATIONS.LOGOUT](state: AuthState) {
    state.originalFromBackend = null;
    state.UIfromOriginal = null;
    state.modifiedToBackend = null;
    state.token = null;
    state.error = null;
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },
};
