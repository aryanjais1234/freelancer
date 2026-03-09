import { clientApi } from '../../../api/client.api';
import { CLIENT_MUTATIONS } from './mutation-types';
import { mapClientBackendToUI } from '../../../mappers/client.mapper';
import { mapProjectBackendToUI } from '../../../mappers/project.mapper';
import type { ClientModified } from '../../../types/client.types';
import type { ProjectModified } from '../../../types/project.types';

export const actions = {
  async createProfile({ commit }: any, data: ClientModified) {
    commit(CLIENT_MUTATIONS.SET_LOADING, true);
    commit(CLIENT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await clientApi.createProfile(data);
      const backend = res.data;
      commit(CLIENT_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(CLIENT_MUTATIONS.SET_PROFILE_UI, mapClientBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(CLIENT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(CLIENT_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchProfile({ commit }: any, userId: number) {
    commit(CLIENT_MUTATIONS.SET_LOADING, true);
    commit(CLIENT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await clientApi.getProfile(userId);
      const backend = res.data;
      commit(CLIENT_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(CLIENT_MUTATIONS.SET_PROFILE_UI, mapClientBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(CLIENT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(CLIENT_MUTATIONS.SET_LOADING, false);
    }
  },

  async updateProfile({ commit }: any, { userId, data }: { userId: number; data: Partial<ClientModified> }) {
    commit(CLIENT_MUTATIONS.SET_LOADING, true);
    commit(CLIENT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await clientApi.updateProfile(userId, data);
      const backend = res.data;
      commit(CLIENT_MUTATIONS.SET_PROFILE_ORIGINAL, backend);
      commit(CLIENT_MUTATIONS.SET_PROFILE_UI, mapClientBackendToUI(backend));
      return backend;
    } catch (err: any) {
      commit(CLIENT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(CLIENT_MUTATIONS.SET_LOADING, false);
    }
  },

  async createProject({ commit }: any, { data, username, userId }: { data: ProjectModified; username: string; userId: number }) {
    commit(CLIENT_MUTATIONS.SET_LOADING, true);
    commit(CLIENT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await clientApi.createProject(data, username, userId);
      return res.data;
    } catch (err: any) {
      commit(CLIENT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(CLIENT_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchProjects({ commit }: any, userId: number) {
    commit(CLIENT_MUTATIONS.SET_LOADING, true);
    commit(CLIENT_MUTATIONS.SET_ERROR, null);
    try {
      const res = await clientApi.getProjects(userId);
      const projects = (res.data || []).map(mapProjectBackendToUI);
      commit(CLIENT_MUTATIONS.SET_PROJECTS, projects);
      return projects;
    } catch (err: any) {
      commit(CLIENT_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(CLIENT_MUTATIONS.SET_LOADING, false);
    }
  },
};
