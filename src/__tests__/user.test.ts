import { UserProfile, UpdateUserProfileRequest } from '../types/user';

describe('UserProfile types', () => {
  it('allows creating valid UserProfile object', () => {
    const profile: UserProfile = {
      uid: 'user-789',
      displayName: 'Alice Smith',
      bio: 'Pastry chef',
      avatarUrl: 'https://example.com/alice.png',
      visibility: 'PUBLIC',
      publicRecipeCount: 12,
      followerCount: 42,
      followingCount: 15,
      isFollowedByCurrentUser: false,
      createdAt: '2026-02-14T10:00:00Z',
      updatedAt: '2026-08-26T12:00:00Z',
    };

    expect(profile.uid).toBe('user-789');
    expect(profile.displayName).toBe('Alice Smith');
    expect(profile.visibility).toBe('PUBLIC');
    expect(profile.followerCount).toBe(42);
  });

  it('allows creating valid UpdateUserProfileRequest object', () => {
    const request: UpdateUserProfileRequest = {
      displayName: 'Alice S.',
      bio: 'Master Baker',
      visibility: 'PRIVATE',
    };

    expect(request.displayName).toBe('Alice S.');
    expect(request.visibility).toBe('PRIVATE');
  });
});
