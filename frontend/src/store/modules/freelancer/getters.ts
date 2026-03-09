import type { FreelancerState } from './state';

export const getters = {
  profile: (state: FreelancerState) => state.UIfromOriginal,
  allFreelancers: (state: FreelancerState) => state.allFreelancers,
  searchResults: (state: FreelancerState) => state.searchResults,
  isLoading: (state: FreelancerState) => state.isLoading,
  error: (state: FreelancerState) => state.error,
  hasProfile: (state: FreelancerState) => !!state.UIfromOriginal,
};
