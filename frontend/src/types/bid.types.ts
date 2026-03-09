export type BidStatus = 'PENDING' | 'ACCEPTED' | 'REJECTED';

export interface BidBackend {
  bidId: number;
  projectId: number;
  freelancerId: number;
  proposalText: string;
  bidAmount: number;
  status: BidStatus;
  createdAt: string;
  updatedAt: string;
}

export interface BidUI {
  bidId: number;
  projectId: number;
  freelancerId: number;
  proposalText: string;
  bidAmount: number;
  status: BidStatus;
  createdAt: Date;
  updatedAt: Date;
}

export interface BidModified {
  projectId: number;
  freelancerId: number;
  proposalText: string;
  bidAmount: number;
}
