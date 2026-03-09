import type { BidState } from './state';
import { BID_MUTATIONS } from './mutation-types';
import type { BidBackend, BidUI, BidModified } from '../../../types/bid.types';

export const mutations = {
  [BID_MUTATIONS.SET_LOADING](state: BidState, loading: boolean) {
    state.isLoading = loading;
  },
  [BID_MUTATIONS.SET_ERROR](state: BidState, error: string | null) {
    state.error = error;
  },
  [BID_MUTATIONS.SET_BID_ORIGINAL](state: BidState, bid: BidBackend | null) {
    state.originalFromBackend = bid;
  },
  [BID_MUTATIONS.SET_BID_UI](state: BidState, bid: BidUI | null) {
    state.UIfromOriginal = bid;
  },
  [BID_MUTATIONS.SET_BID_MODIFIED](state: BidState, bid: BidModified | null) {
    state.modifiedToBackend = bid;
  },
  [BID_MUTATIONS.SET_PROJECT_BIDS](state: BidState, bids: BidUI[]) {
    state.projectBids = bids;
  },
  [BID_MUTATIONS.SET_FREELANCER_BIDS](state: BidState, bids: BidUI[]) {
    state.freelancerBids = bids;
  },
};
