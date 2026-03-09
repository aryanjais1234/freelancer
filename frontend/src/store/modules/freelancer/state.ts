import type { FreelancerBackend, FreelancerUI, FreelancerModified } from '../../../types/freelancer.types';

export interface FreelancerState {
  originalFromBackend: FreelancerBackend | null;
  UIfromOriginal: FreelancerUI | null;
  modifiedToBackend: FreelancerModified | null;
  allFreelancers: FreelancerUI[];
  searchResults: FreelancerUI[];
  isLoading: boolean;
  error: string | null;
}

export const state = (): FreelancerState => ({
  originalFromBackend: null,
  UIfromOriginal: null,
  modifiedToBackend: null,
  allFreelancers: [],
  searchResults: [],
  isLoading: false,
  error: null,
});
