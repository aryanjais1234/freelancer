export interface FreelancerBackend {
  freelancerId: number;
  userId: number;
  name: string;
  email: string;
  skills: string;
  rating: number;
  completedProjects: number;
  availabilityStatus: 'AVAILABLE' | 'BUSY';
  profileSummary: string;
  portfolioLink: string;
  location: string;
  profilePictureUrl: string;
  phoneNumber: string;
  socialLinks: string;
  certifications: string;
  languages: string;
  hourlyRate: string;
  education: string;
  workExperience: string;
  additionalInfo: string;
  createdAt: string;
  updatedAt: string;
}

export interface FreelancerUI {
  freelancerId: number;
  userId: number;
  name: string;
  email: string;
  skills: string[];
  rating: number;
  completedProjects: number;
  availabilityStatus: 'AVAILABLE' | 'BUSY';
  profileSummary: string;
  portfolioLink: string;
  location: string;
  profilePictureUrl: string;
  phoneNumber: string;
  socialLinks: string;
  certifications: string;
  languages: string;
  hourlyRate: string;
  education: string;
  workExperience: string;
  additionalInfo: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface FreelancerModified {
  userId: number;
  name: string;
  email: string;
  skills: string;
  availabilityStatus: 'AVAILABLE' | 'BUSY';
  profileSummary: string;
  portfolioLink: string;
  location: string;
  profilePictureUrl: string;
  phoneNumber: string;
  socialLinks: string;
  certifications: string;
  languages: string;
  hourlyRate: string;
  education: string;
  workExperience: string;
  additionalInfo: string;
}
