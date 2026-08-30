export type NotificationType = 'RECIPE_LIKE' | 'RECIPE_RATING' | 'RECIPE_COMMENT' | 'NEW_FOLLOWER';

export interface SocialNotification {
  id: string;
  recipientUid: string;
  actorUid: string;
  actorName: string;
  actorAvatarUrl?: string;
  eventType: NotificationType;
  targetRecipeId?: string;
  targetRecipeName?: string;
  contentSnippet?: string;
  isRead: boolean;
  createdAt: string;
}

export interface NotificationsResponse {
  unreadCount: number;
  notifications: SocialNotification[];
  hasMore: boolean;
}
