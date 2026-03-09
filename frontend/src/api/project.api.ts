import apiClient from './index';

export const projectApi = {
  getAll() {
    return apiClient.get('/projects/all');
  },

  getOpen() {
    return apiClient.get('/projects/open');
  },

  getById(id: number) {
    return apiClient.get(`/projects/getProject/${id}`);
  },

  getByClient(clientId: number) {
    return apiClient.get(`/projects/client/${clientId}`);
  },

  getByCategory(category: string) {
    return apiClient.get(`/projects/category/${category}`);
  },

  updateStatus(id: number, status: string, assignedFreelancerId?: number) {
    const params: Record<string, string | number> = { status };
    if (assignedFreelancerId !== undefined) {
      params.assignedFreelancerId = assignedFreelancerId;
    }
    return apiClient.put(`/projects/${id}/status`, null, { params });
  },
};
