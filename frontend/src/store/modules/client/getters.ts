import type { ClientState } from './state';

export const getters = {
  profile: (state: ClientState) => state.UIfromOriginal,
  projects: (state: ClientState) => state.projects,
  isLoading: (state: ClientState) => state.isLoading,
  error: (state: ClientState) => state.error,
  hasProfile: (state: ClientState) => !!state.UIfromOriginal,
};
