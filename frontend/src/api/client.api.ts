import apiClient from './index';
import type { ClientModified } from '../types/client.types';
import type { ProjectModified } from '../types/project.types';

export const clientApi = {
  createProfile(data: ClientModified) {
    return apiClient.post('/clients/create', data);
  },

  getProfile(userId: number) {
    return apiClient.get(`/clients/profile/${userId}`);
  },

  updateProfile(userId: number, data: Partial<ClientModified>) {
    return apiClient.put(`/clients/profile/${userId}`, data);
  },

  createProject(data: ProjectModified, username: string, userId: number) {
    return apiClient.post('/clients/createProject', data, {
      headers: { username, userId: String(userId) },
    });
  },

  getProjects(userId: number) {
    return apiClient.get(`/clients/projects/${userId}`);
  },

  getClientDetails(id: number) {
    return apiClient.get(`/clients/getClientDetails/${id}`);
  },
};
