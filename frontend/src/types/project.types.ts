export type ProjectStatus = 'OPEN' | 'IN_PROGRESS' | 'COMPLETED';

export interface ProjectBackend {
  id: number;
  clientId: number;
  title: string;
  description: string;
  budget: number;
  budgetMin: number;
  budgetMax: number;
  category: string;
  deadline: string;
  duration: string;
  status: ProjectStatus;
  assignedFreelancerId: number;
  createdAt: string;
  updatedAt: string;
}

export interface ProjectUI {
  id: number;
  clientId: number;
  title: string;
  description: string;
  budget: number;
  budgetMin: number;
  budgetMax: number;
  category: string;
  deadline: Date | null;
  duration: string;
  status: ProjectStatus;
  assignedFreelancerId: number | null;
  createdAt: Date;
  updatedAt: Date;
}

export interface ProjectModified {
  clientId: number;
  title: string;
  description: string;
  budget: number;
  budgetMin: number;
  budgetMax: number;
  category: string;
  deadline: string;
  duration: string;
  status?: ProjectStatus;
}
