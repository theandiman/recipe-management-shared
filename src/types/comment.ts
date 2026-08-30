export interface Comment {
  id: string;
  recipeId: string;
  userId: string;
  authorName?: string;
  authorAvatarUrl?: string;
  content: string;
  parentId?: string | null;
  likeCount?: number;
  createdAt: string;
  updatedAt: string;
  replies?: Comment[];
}

export interface CommentsResponse {
  totalComments: number;
  comments: Comment[];
  hasMore: boolean;
}

export interface CreateCommentRequest {
  content: string;
  parentId?: string | null;
}

export interface UpdateCommentRequest {
  content: string;
}
