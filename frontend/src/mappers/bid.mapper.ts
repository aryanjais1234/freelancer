import type { BidBackend, BidUI, BidModified } from '../types/bid.types';

export const mapBidBackendToUI = (backend: BidBackend): BidUI => ({
  bidId: backend.bidId,
  projectId: backend.projectId,
  freelancerId: backend.freelancerId,
  proposalText: backend.proposalText,
  bidAmount: backend.bidAmount,
  status: backend.status,
  createdAt: new Date(backend.createdAt),
  updatedAt: new Date(backend.updatedAt),
});

export const mapBidUIToModified = (ui: BidUI): BidModified => ({
  projectId: ui.projectId,
  freelancerId: ui.freelancerId,
  proposalText: ui.proposalText,
  bidAmount: ui.bidAmount,
});
