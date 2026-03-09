import apiClient from './index';
import type { BidModified } from '../types/bid.types';

export const bidApi = {
  submit(data: BidModified) {
    return apiClient.post('/bids/submit', data);
  },

  getByProject(projectId: number) {
    return apiClient.get(`/bids/project/${projectId}`);
  },

  getByFreelancer(freelancerId: number) {
    return apiClient.get(`/bids/freelancer/${freelancerId}`);
  },

  getById(bidId: number) {
    return apiClient.get(`/bids/${bidId}`);
  },

  accept(bidId: number) {
    return apiClient.put(`/bids/${bidId}/accept`);
  },

  reject(bidId: number) {
    return apiClient.put(`/bids/${bidId}/reject`);
  },
};
