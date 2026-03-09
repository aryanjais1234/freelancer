import type { ProjectState } from './state';
import { PROJECT_MUTATIONS } from './mutation-types';
import type { ProjectBackend, ProjectUI, ProjectModified } from '../../../types/project.types';

export const mutations = {
  [PROJECT_MUTATIONS.SET_LOADING](state: ProjectState, loading: boolean) {
    state.isLoading = loading;
  },
  [PROJECT_MUTATIONS.SET_ERROR](state: ProjectState, error: string | null) {
    state.error = error;
  },
  [PROJECT_MUTATIONS.SET_PROJECT_ORIGINAL](state: ProjectState, project: ProjectBackend | null) {
    state.originalFromBackend = project;
  },
  [PROJECT_MUTATIONS.SET_PROJECT_UI](state: ProjectState, project: ProjectUI | null) {
    state.UIfromOriginal = project;
  },
  [PROJECT_MUTATIONS.SET_PROJECT_MODIFIED](state: ProjectState, project: ProjectModified | null) {
    state.modifiedToBackend = project;
  },
  [PROJECT_MUTATIONS.SET_ALL_PROJECTS](state: ProjectState, projects: ProjectUI[]) {
    state.allProjects = projects;
  },
  [PROJECT_MUTATIONS.SET_OPEN_PROJECTS](state: ProjectState, projects: ProjectUI[]) {
    state.openProjects = projects;
  },
};
