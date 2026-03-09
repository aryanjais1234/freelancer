import { authApi } from '../../../api/auth.api';
import { AUTH_MUTATIONS } from './mutation-types';
import { mapUserBackendToUI } from '../../../mappers/user.mapper';
import type { RegisterPayload, LoginPayload } from '../../../types/user.types';

function parseJwt(token: string) {
  try {
    const base64Url = token.split('.')[1] ?? '';
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch {
    return null;
  }
}

export const actions = {
  async register({ commit }: any, payload: RegisterPayload) {
    commit(AUTH_MUTATIONS.SET_LOADING, true);
    commit(AUTH_MUTATIONS.SET_ERROR, null);
    try {
      const response = await authApi.register(payload);
      const userBackend = response.data;
      commit(AUTH_MUTATIONS.SET_USER_ORIGINAL, userBackend);
      commit(AUTH_MUTATIONS.SET_USER_UI, mapUserBackendToUI(userBackend));
      return userBackend;
    } catch (err: any) {
      const msg = err.response?.data?.message || err.message || 'Registration failed';
      commit(AUTH_MUTATIONS.SET_ERROR, msg);
      throw err;
    } finally {
      commit(AUTH_MUTATIONS.SET_LOADING, false);
    }
  },

  async login({ commit }: any, payload: LoginPayload) {
    commit(AUTH_MUTATIONS.SET_LOADING, true);
    commit(AUTH_MUTATIONS.SET_ERROR, null);
    try {
      const response = await authApi.login(payload);
      const token = typeof response.data === 'string' ? response.data : response.data.token;
      commit(AUTH_MUTATIONS.SET_TOKEN, token);

      const decoded = parseJwt(token);
      if (decoded) {
        const userUI = {
          id: decoded.id || decoded.userId || 0,
          fullName: decoded.name || decoded.sub || '',
          email: decoded.sub || decoded.userName || '',
          role: decoded.role || decoded.roles?.[0]?.replace('ROLE_', '') || 'FREELANCER',
          createdAt: new Date(),
        };
        commit(AUTH_MUTATIONS.SET_USER_UI, userUI);
      }
      return token;
    } catch (err: any) {
      const msg = err.response?.data?.message || err.message || 'Login failed';
      commit(AUTH_MUTATIONS.SET_ERROR, msg);
      throw err;
    } finally {
      commit(AUTH_MUTATIONS.SET_LOADING, false);
    }
  },

  restoreSession({ commit }: any) {
    const token = localStorage.getItem('token');
    const userStr = localStorage.getItem('user');
    if (token) {
      commit(AUTH_MUTATIONS.SET_TOKEN, token);
    }
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        if (user.createdAt) user.createdAt = new Date(user.createdAt);
        commit(AUTH_MUTATIONS.SET_USER_UI, user);
      } catch {
        // ignore malformed data
      }
    }
  },

  logout({ commit }: any) {
    commit(AUTH_MUTATIONS.LOGOUT);
  },
};
