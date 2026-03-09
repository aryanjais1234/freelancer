import apiClient from './index';
import type { FreelancerModified } from '../types/freelancer.types';

export const freelancerApi = {
  createProfile(data: FreelancerModified) {
    return apiClient.post('/freelancers/create', data);
  },

  getProfile(userId: number) {
    return apiClient.get(`/freelancers/profile/${userId}`);
  },

  updateProfile(userId: number, data: Partial<FreelancerModified>) {
    return apiClient.put(`/freelancers/profile/${userId}`, data);
  },

  searchBySkill(skill: string) {
    return apiClient.get('/freelancers/search', { params: { skill } });
  },

  getAll() {
    return apiClient.get('/freelancers/all');
  },

  getProject(id: number) {
    return apiClient.get(`/freelancers/getProject/${id}`);
  },

  getOpenProjects() {
    return apiClient.get('/freelancers/projects/open');
  },
};
