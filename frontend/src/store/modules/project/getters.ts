import type { ProjectState } from './state';

export const getters = {
  currentProject: (state: ProjectState) => state.UIfromOriginal,
  allProjects: (state: ProjectState) => state.allProjects,
  openProjects: (state: ProjectState) => state.openProjects,
  isLoading: (state: ProjectState) => state.isLoading,
  error: (state: ProjectState) => state.error,
};
