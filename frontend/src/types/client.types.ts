export interface ClientBackend {
  id: number;
  userId: number;
  name: string;
  email: string;
  companyName: string;
  website: string;
  bio: string;
  rating: number;
  projectIds: number[];
  createdAt: string;
  updatedAt: string;
}

export interface ClientUI {
  id: number;
  userId: number;
  name: string;
  email: string;
  companyName: string;
  website: string;
  bio: string;
  rating: number;
  projectIds: number[];
  createdAt: Date;
  updatedAt: Date;
}

export interface ClientModified {
  userId: number;
  name: string;
  email: string;
  companyName: string;
  website: string;
  bio: string;
}
