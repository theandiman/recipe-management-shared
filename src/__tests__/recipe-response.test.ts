import { Recipe, RecipeResponse, PagedRecipeResponse, AuthorDto } from '../types/recipe';

describe('RecipeResponse DTO contracts', () => {
  it('should instantiate a valid RecipeResponse matching the backend response contract', () => {
    const response: RecipeResponse = {
      id: 'recipe-123',
      userId: 'user-456',
      recipeName: 'Spaghetti Carbonara',
      title: 'Spaghetti Carbonara',
      description: 'Classic Italian pasta',
      ingredients: ['Spaghetti', 'Eggs', 'Pecorino', 'Guanciale'],
      instructions: ['Boil pasta', 'Fry guanciale', 'Mix with eggs and cheese'],
      servings: 4,
      source: 'manual',
      isPublic: true,
      authorDisplayName: 'Chef Mario',
      authorAvatarUrl: 'https://example.com/mario.jpg',
      author: {
        uid: 'user-456',
        displayName: 'Chef Mario',
        avatarUrl: 'https://example.com/mario.jpg'
      },
      likeCount: 42,
      isLikedByCurrentUser: true,
      isSavedByCurrentUser: false,
      averageRating: 4.8,
      ratingCount: 15
    };

    expect(response.id).toBe('recipe-123');
    expect(response.recipeName).toBe('Spaghetti Carbonara');
    expect(response.title).toBe('Spaghetti Carbonara');
    expect(response.authorDisplayName).toBe('Chef Mario');
    expect(response.author?.displayName).toBe('Chef Mario');
    expect(response.likeCount).toBe(42);
    expect(response.isLikedByCurrentUser).toBe(true);
  });

  it('should instantiate a valid PagedRecipeResponse', () => {
    const paged: PagedRecipeResponse = {
      recipes: [
        {
          id: 'r1',
          recipeName: 'Pizza Margherita',
          ingredients: ['Dough', 'Tomato', 'Mozzarella'],
          instructions: ['Bake at 450F'],
          servings: 2,
          source: 'manual',
          authorDisplayName: 'Luigi'
        }
      ],
      size: 1,
      totalCount: 10,
      nextPageToken: 'cursor-token-abc'
    };

    expect(paged.recipes).toHaveLength(1);
    expect(paged.size).toBe(1);
    expect(paged.totalCount).toBe(10);
    expect(paged.nextPageToken).toBe('cursor-token-abc');
  });
});
