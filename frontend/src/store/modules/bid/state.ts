import type { BidBackend, BidUI, BidModified } from '../../../types/bid.types';

export interface BidState {
  originalFromBackend: BidBackend | null;
  UIfromOriginal: BidUI | null;
  modifiedToBackend: BidModified | null;
  projectBids: BidUI[];
  freelancerBids: BidUI[];
  isLoading: boolean;
  error: string | null;
}

export const state = (): BidState => ({
  originalFromBackend: null,
  UIfromOriginal: null,
  modifiedToBackend: null,
  projectBids: [],
  freelancerBids: [],
  isLoading: false,
  error: null,
});
