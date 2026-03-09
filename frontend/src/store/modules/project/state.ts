import type { ProjectBackend, ProjectUI, ProjectModified } from '../../../types/project.types';

export interface ProjectState {
  originalFromBackend: ProjectBackend | null;
  UIfromOriginal: ProjectUI | null;
  modifiedToBackend: ProjectModified | null;
  allProjects: ProjectUI[];
  openProjects: ProjectUI[];
  isLoading: boolean;
  error: string | null;
}

export const state = (): ProjectState => ({
  originalFromBackend: null,
  UIfromOriginal: null,
  modifiedToBackend: null,
  allProjects: [],
  openProjects: [],
  isLoading: false,
  error: null,
});
