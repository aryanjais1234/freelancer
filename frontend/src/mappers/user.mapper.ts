import type { UserBackend, UserUI, UserModified } from '../types/user.types';

export const mapUserBackendToUI = (backend: UserBackend): UserUI => ({
  id: backend.id,
  fullName: backend.name,
  email: backend.userName,
  role: backend.role,
  createdAt: new Date(backend.createdAt),
});

export const mapUserUIToModified = (ui: UserUI): UserModified => ({
  name: ui.fullName,
  userName: ui.email,
  role: ui.role,
});
