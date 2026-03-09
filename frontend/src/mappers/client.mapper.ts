import type { ClientBackend, ClientUI, ClientModified } from '../types/client.types';

export const mapClientBackendToUI = (backend: ClientBackend): ClientUI => ({
  id: backend.id,
  userId: backend.userId,
  name: backend.name,
  email: backend.email,
  companyName: backend.companyName || '',
  website: backend.website || '',
  bio: backend.bio || '',
  rating: backend.rating || 0,
  projectIds: backend.projectIds || [],
  createdAt: new Date(backend.createdAt),
  updatedAt: new Date(backend.updatedAt),
});

export const mapClientUIToModified = (ui: ClientUI): ClientModified => ({
  userId: ui.userId,
  name: ui.name,
  email: ui.email,
  companyName: ui.companyName,
  website: ui.website,
  bio: ui.bio,
});
