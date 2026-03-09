import type { ClientBackend, ClientUI, ClientModified } from '../../../types/client.types';
import type { ProjectUI } from '../../../types/project.types';

export interface ClientState {
  originalFromBackend: ClientBackend | null;
  UIfromOriginal: ClientUI | null;
  modifiedToBackend: ClientModified | null;
  projects: ProjectUI[];
  isLoading: boolean;
  error: string | null;
}

export const state = (): ClientState => ({
  originalFromBackend: null,
  UIfromOriginal: null,
  modifiedToBackend: null,
  projects: [],
  isLoading: false,
  error: null,
});
