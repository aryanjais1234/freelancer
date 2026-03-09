import { freelancerApi } from '../../../api/freelancer.api';
import { FREELANCER_MUTATIONS } from './mutation-types';
import { mapFreelancerBackendToUI } from '../../../mappers/freelancer.mapper';
import type { FreelancerModified } from '../../../types/freelancer.types';

export const actions = {
  async createProfile({ commit }: any, data: FreelancerModified) {
    commit(FREELANCER_MUTATIONS.SET_LOADING, true);
    commit(FREELANCER_MUTATIONS.SET_ERROR, null);
    try {
      const res = await freelancerApi.createProfile(data);
      const backend = res.data;
      commit(FREELANCER_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(FREELANCER_MUTATIONS.SET_PROFILE_UI, mapFreelancerBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(FREELANCER_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(FREELANCER_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchProfile({ commit }: any, userId: number) {
    commit(FREELANCER_MUTATIONS.SET_LOADING, true);
    commit(FREELANCER_MUTATIONS.SET_ERROR, null);
    try {
      const res = await freelancerApi.getProfile(userId);
      const backend = res.data;
      commit(FREELANCER_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(FREELANCER_MUTATIONS.SET_PROFILE_UI, mapFreelancerBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(FREELANCER_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(FREELANCER_MUTATIONS.SET_LOADING, false);
    }
  },

  async updateProfile({ commit }: any, { userId, data }: { userId: number; data: Partial<FreelancerModified> }) {
    commit(FREELANCER_MUTATIONS.SET_LOADING, true);
    commit(FREELANCER_MUTATIONS.SET_ERROR, null);
    try {
      const res = await freelancerApi.updateProfile(userId, data);
      const backend = res.data;
      commit(FREELANCER_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(FREELANCER_MUTATIONS.SET_PROFILE_UI, mapFreelancerBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(FREELANCER_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(FREELANCER_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchAll({ commit }: any) {
    commit(FREELANCER_MUTATIONS.SET_LOADING, true);
    commit(FREELANCER_MUTATIONS.SET_ERROR, null);
    try {
      const res = await freelancerApi.getAll();
      const freelancers = (res.data || []).map(mapFreelancerBackendToUI);
      commit(FREELANCER_MUTATIONS.SET_ALL_FREELANCERS, freelancers);
      return freelancers;
    } catch (err: any) {
      commit(FREELANCER_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(FREELANCER_MUTATIONS.SET_LOADING, false);
    }
  },

  async searchBySkill({ commit }: any, skill: string) {
    commit(FREELANCER_MUTATIONS.SET_LOADING, true);
    commit(FREELANCER_MUTATIONS.SET_ERROR, null);
    try {
      const res = await freelancerApi.searchBySkill(skill);
      const results = (res.data || []).map(mapFreelancerBackendToUI);
      commit(FREELANCER_MUTATIONS.SET_SEARCH_RESULTS, results);
      return results;
    } catch (err: any) {
      commit(FREELANCER_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(FREELANCER_MUTATIONS.SET_LOADING, false);
    }
  },
};
