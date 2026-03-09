import type { BidState } from './state';

export const getters = {
  currentBid: (state: BidState) => state.UIfromOriginal,
  projectBids: (state: BidState) => state.projectBids,
  freelancerBids: (state: BidState) => state.freelancerBids,
  isLoading: (state: BidState) => state.isLoading,
  error: (state: BidState) => state.error,
};
