import type { ClientState } from './state';
import { CLIENT_MUTATIONS } from './mutation-types';
import type { ClientBackend, ClientUI, ClientModified } from '../../../types/client.types';
import type { ProjectUI } from '../../../types/project.types';

export const mutations = {
  [CLIENT_MUTATIONS.SET_LOADING](state: ClientState, loading: boolean) {
    state.isLoading = loading;
  },
  [CLIENT_MUTATIONS.SET_ERROR](state: ClientState, error: string | null) {
    state.error = error;
  },
  [CLIENT_MUTATIONS.SET_PROFILE_ORIGINAL](state: ClientState, profile: ClientBackend | null) {
    state.originalFromBackend = profile;
  },
  [CLIENT_MUTATIONS.SET_PROFILE_UI](state: ClientState, profile: ClientUI | null) {
    state.UIfromOriginal = profile;
  },
  [CLIENT_MUTATIONS.SET_PROFILE_MODIFIED](state: ClientState, profile: ClientModified | null) {
    state.modifiedToBackend = profile;
  },
  [CLIENT_MUTATIONS.SET_PROJECTS](state: ClientState, projects: ProjectUI[]) {
    state.projects = projects;
  },
};
