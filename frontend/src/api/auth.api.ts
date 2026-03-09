import apiClient from './index';
import type { RegisterPayload, LoginPayload } from '../types/user.types';

export const authApi = {
  register(payload: RegisterPayload) {
    return apiClient.post('/users/register', payload);
  },

  login(payload: LoginPayload) {
    return apiClient.post('/users/login', payload);
  },
};
