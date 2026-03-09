import { projectApi } from '../../../api/project.api';
import { PROJECT_MUTATIONS } from './mutation-types';
import { mapProjectBackendToUI } from '../../../mappers/project.mapper';

export const actions = {
  async fetchAll({ commit }: any) {
    commit(PROJECT_MUTATIONS.SET_LOADING, true);
    commit(PROJECT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await projectApi.getAll();
      const projects = (res.data || []).map(mapProjectBackendToUI);
      commit(PROJECT_MUTATIONS.SET_ALL_PROJECTS, projects);
      return projects;
    } catch (err: any) {
      commit(PROJECT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(PROJECT_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchOpen({ commit }: any) {
    commit(PROJECT_MUTATIONS.SET_LOADING, true);
    commit(PROJECT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await projectApi.getOpen();
      const projects = (res.data || []).map(mapProjectBackendToUI);
      commit(PROJECT_MUTATIONS.SET_OPEN_PROJECTS, projects);
      return projects;
    } catch (err: any) {
      commit(PROJECT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(PROJECT_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchById({ commit }: any, id: number) {
    commit(PROJECT_MUTATIONS.SET_LOADING, true);
    commit(PROJECT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await projectApi.getById(id);
      const project = mapProjectBackendToUI(res.data);
      commit(PROJECT_MUTATIONS.SET_PROJECT_ORIGINAL, res.data);
      commit(PROJECT_MUTATIONS.SET_PROJECT_UI, project);
      return project;
    } catch (err: any) {
      commit(PROJECT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(PROJECT_MUTATIONS.SET_LOADING, false);
    }
  },

  async updateStatus({ commit }: any, { id, status, assignedFreelancerId }: { id: number; status: string; assignedFreelancerId?: number }) {
    commit(PROJECT_MUTATIONS.SET_LOADING, true);
    commit(PROJECT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await projectApi.updateStatus(id, status, assignedFreelancerId);
      return res.data;
    } catch (err: any) {
      commit(PROJECT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(PROJECT_MUTATIONS.SET_LOADING, false);
    }
  },
};
