import type { FreelancerState } from './state';
import { FREELANCER_MUTATIONS } from './mutation-types';
import type { FreelancerBackend, FreelancerUI, FreelancerModified } from '../../../types/freelancer.types';

export const mutations = {
  [FREELANCER_MUTATIONS.SET_LOADING](state: FreelancerState, loading: boolean) {
    state.isLoading = loading;
  },
  [FREELANCER_MUTATIONS.SET_ERROR](state: FreelancerState, error: string | null) {
    state.error = error;
  },
  [FREELANCER_MUTATIONS.SET_PROFILE_ORIGINAL](state: FreelancerState, profile: FreelancerBackend | null) {
    state.originalFromBackend = profile;
  },
  [FREELANCER_MUTATIONS.SET_PROFILE_UI](state: FreelancerState, profile: FreelancerUI | null) {
    state.UIfromOriginal = profile;
  },
  [FREELANCER_MUTATIONS.SET_PROFILE_MODIFIED](state: FreelancerState, profile: FreelancerModified | null) {
    state.modifiedToBackend = profile;
  },
  [FREELANCER_MUTATIONS.SET_ALL_FREELANCERS](state: FreelancerState, freelancers: FreelancerUI[]) {
    state.allFreelancers = freelancers;
  },
  [FREELANCER_MUTATIONS.SET_SEARCH_RESULTS](state: FreelancerState, results: FreelancerUI[]) {
    state.searchResults = results;
  },
};
