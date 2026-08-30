export interface Rating {
  id: string;
  recipeId: string;
  userId: string;
  authorName?: string;
  authorAvatarUrl?: string;
  score: number;
  reviewText?: string;
  createdAt: string;
  updatedAt: string;
}

export interface RatingDistribution {
  1: number;
  2: number;
  3: number;
  4: number;
  5: number;
}

export interface RecipeRatingsResponse {
  averageRating: number;
  ratingCount: number;
  distribution: RatingDistribution;
  ratings: Rating[];
  hasMore: boolean;
}

export interface CreateRatingRequest {
  score: number;
  reviewText?: string;
}
