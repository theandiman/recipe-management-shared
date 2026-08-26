/**
 * Shared UserProfile model representing the canonical user profile contract.
 */

export interface UserProfile {
  uid: string;
  displayName?: string;
  bio?: string;
  avatarUrl?: string;
  visibility?: 'PUBLIC' | 'PRIVATE' | string;
  publicRecipeCount?: number;
  followerCount?: number;
  followingCount?: number;
  isFollowedByCurrentUser?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface UpdateUserProfileRequest {
  displayName?: string;
  bio?: string;
  avatarUrl?: string;
  visibility?: 'PUBLIC' | 'PRIVATE' | string;
}
