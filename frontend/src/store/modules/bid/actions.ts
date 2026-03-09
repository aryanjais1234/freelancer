import { bidApi } from '../../../api/bid.api';
import { BID_MUTATIONS } from './mutation-types';
import { mapBidBackendToUI } from '../../../mappers/bid.mapper';
import type { BidModified } from '../../../types/bid.types';

export const actions = {
  async submitBid({ commit }: any, data: BidModified) {
    commit(BID_MUTATIONS.SET_LOADING, true);
    commit(BID_MUTATIONS.SET_ERROR, null);
    try {
      const res = await bidApi.submit(data);
      const bid = mapBidBackendToUI(res.data);
      commit(BID_MUTATIONS.SET_BID_ORIGINAL, res.data);
      commit(BID_MUTATIONS.SET_BID_UI, bid);
      return bid;
    } catch (err: any) {
      commit(BID_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(BID_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchByProject({ commit }: any, projectId: number) {
    commit(BID_MUTATIONS.SET_LOADING, true);
    commit(BID_MUTATIONS.SET_ERROR, null);
    try {
      const res = await bidApi.getByProject(projectId);
      const bids = (res.data || []).map(mapBidBackendToUI);
      commit(BID_MUTATIONS.SET_PROJECT_BIDS, bids);
      return bids;
    } catch (err: any) {
      commit(BID_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(BID_MUTATIONS.SET_LOADING, false);
    }
  },

  async fetchByFreelancer({ commit }: any, freelancerId: number) {
    commit(BID_MUTATIONS.SET_LOADING, true);
    commit(BID_MUTATIONS.SET_ERROR, null);
    try {
      const res = await bidApi.getByFreelancer(freelancerId);
      const bids = (res.data || []).map(mapBidBackendToUI);
      commit(BID_MUTATIONS.SET_FREELANCER_BIDS, bids);
      return bids;
    } catch (err: any) {
      commit(BID_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(BID_MUTATIONS.SET_LOADING, false);
    }
  },

  async acceptBid({ commit }: any, bidId: number) {
    commit(BID_MUTATIONS.SET_LOADING, true);
    commit(BID_MUTATIONS.SET_ERROR, null);
    try {
      const res = await bidApi.accept(bidId);
      return res.data;
    } catch (err: any) {
      commit(BID_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(BID_MUTATIONS.SET_LOADING, false);
    }
  },

  async rejectBid({ commit }: any, bidId: number) {
    commit(BID_MUTATIONS.SET_LOADING, true);
    commit(BID_MUTATIONS.SET_ERROR, null);
    try {
      const res = await bidApi.reject(bidId);
      return res.data;
    } catch (err: any) {
      commit(BID_MUTATIONS.SET_ERROR, err.response?.data?.message || err.message);
      throw err;
    } finally {
      commit(BID_MUTATIONS.SET_LOADING, false);
    }
  },
};
