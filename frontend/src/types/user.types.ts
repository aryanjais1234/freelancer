export interface UserBackend {
  id: number;
  name: string;
  userName: string;
  role: 'FREELANCER' | 'CLIENT';
  createdAt: string;
  updatedAt: string;
}

export interface UserUI {
  id: number;
  fullName: string;
  email: string;
  role: 'FREELANCER' | 'CLIENT';
  createdAt: Date;
}

export interface UserModified {
  name: string;
  userName: string;
  password?: string;
  role: 'FREELANCER' | 'CLIENT';
}

export interface RegisterPayload {
  name: string;
  userName: string;
  password: string;
  role: 'FREELANCER' | 'CLIENT';
}

export interface LoginPayload {
  userName: string;
  password: string;
}
