import type { ProjectBackend, ProjectUI, ProjectModified } from '../types/project.types';

export const mapProjectBackendToUI = (backend: ProjectBackend): ProjectUI => ({
  id: backend.id,
  clientId: backend.clientId,
  title: backend.title,
  description: backend.description,
  budget: backend.budget || 0,
  budgetMin: backend.budgetMin || 0,
  budgetMax: backend.budgetMax || 0,
  category: backend.category || '',
  deadline: backend.deadline ? new Date(backend.deadline) : null,
  duration: backend.duration || '',
  status: backend.status,
  assignedFreelancerId: backend.assignedFreelancerId || null,
  createdAt: new Date(backend.createdAt),
  updatedAt: new Date(backend.updatedAt),
});

export const mapProjectUIToModified = (ui: ProjectUI): ProjectModified => ({
  clientId: ui.clientId,
  title: ui.title,
  description: ui.description,
  budget: ui.budget,
  budgetMin: ui.budgetMin,
  budgetMax: ui.budgetMax,
  category: ui.category,
  deadline: (ui.deadline ? ui.deadline.toISOString().split('T')[0] : '') as string,
  duration: ui.duration,
  status: ui.status,
});
