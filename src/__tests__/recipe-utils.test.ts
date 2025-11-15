import { RecipeUtils } from '../types/recipe';

describe('RecipeUtils', () => {
  describe('getServingsAsNumber', () => {
    it('should return number when input is number', () => {
      expect(RecipeUtils.getServingsAsNumber(4)).toBe(4);
      expect(RecipeUtils.getServingsAsNumber(2.5)).toBe(2.5);
    });

    it('should parse string to number', () => {
      expect(RecipeUtils.getServingsAsNumber('4')).toBe(4);
      expect(RecipeUtils.getServingsAsNumber('2')).toBe(2);
    });

    it('should handle invalid strings', () => {
      expect(RecipeUtils.getServingsAsNumber('invalid')).toBeNaN();
      expect(RecipeUtils.getServingsAsNumber('')).toBeNaN();
    });
  });

  describe('getCalculatedTotalTimeMinutes', () => {
    it('should return explicit totalTimeMinutes when available', () => {
      const recipe = {
        recipeName: 'Test',
        ingredients: [],
        instructions: [],
        servings: 4,
        source: 'manual',
        prepTimeMinutes: 15,
        cookTimeMinutes: 30,
        totalTimeMinutes: 50
      };

      expect(RecipeUtils.getCalculatedTotalTimeMinutes(recipe)).toBe(50);
    });

    it('should calculate total from prep and cook time when no explicit total', () => {
      const recipe = {
        recipeName: 'Test',
        ingredients: [],
        instructions: [],
        servings: 4,
        source: 'manual',
        prepTimeMinutes: 15,
        cookTimeMinutes: 30
      };

      expect(RecipeUtils.getCalculatedTotalTimeMinutes(recipe)).toBe(45);
    });

    it('should return undefined when no timing information available', () => {
      const recipe = {
        recipeName: 'Test',
        ingredients: [],
        instructions: [],
        servings: 4,
        source: 'manual'
      };

      expect(RecipeUtils.getCalculatedTotalTimeMinutes(recipe)).toBeUndefined();
    });

    it('should handle partial timing information', () => {
      const recipe = {
        recipeName: 'Test',
        ingredients: [],
        instructions: [],
        servings: 4,
        source: 'manual',
        prepTimeMinutes: 15
        // missing cookTimeMinutes
      };

      expect(RecipeUtils.getCalculatedTotalTimeMinutes(recipe)).toBeUndefined();
    });
  });

  describe('instantToDate', () => {
    it('should convert ISO string to Date object', () => {
      const isoString = '2024-01-01T10:00:00Z';
      const date = RecipeUtils.instantToDate(isoString);

      expect(date).toBeInstanceOf(Date);
      expect(date.toISOString()).toBe('2024-01-01T10:00:00.000Z'); // Date adds milliseconds
    });

    it('should handle different ISO formats', () => {
      const isoString = '2024-01-01T10:00:00.000Z';
      const date = RecipeUtils.instantToDate(isoString);

      expect(date).toBeInstanceOf(Date);
      expect(date.getFullYear()).toBe(2024);
      expect(date.getMonth()).toBe(0); // January is 0
      expect(date.getDate()).toBe(1);
    });
  });

  describe('dateToInstantString', () => {
    it('should convert Date to ISO string', () => {
      const date = new Date('2024-01-01T10:00:00Z');
      const isoString = RecipeUtils.dateToInstantString(date);

      expect(isoString).toBe('2024-01-01T10:00:00.000Z');
    });

    it('should handle different Date objects', () => {
      const date = new Date(2024, 0, 1, 10, 0, 0); // January 1, 2024, 10:00:00
      const isoString = RecipeUtils.dateToInstantString(date);

      expect(isoString).toMatch(/^2024-01-01T\d{2}:00:00/);
    });
  });

  describe('integration tests', () => {
    it('should handle complete recipe data transformation', () => {
      const recipe = {
        recipeName: 'Test Recipe',
        servings: 4,
        prepTimeMinutes: 15,
        cookTimeMinutes: 30,
        totalTimeMinutes: 45,
        ingredients: ['ingredient 1', 'ingredient 2'],
        instructions: ['step 1', 'step 2'],
        source: 'manual',
        createdAt: '2024-01-01T10:00:00Z'
      };

      // Test utility functions work together
      const servings = RecipeUtils.getServingsAsNumber(recipe.servings);
      const totalTime = RecipeUtils.getCalculatedTotalTimeMinutes(recipe);
      const createdDate = RecipeUtils.instantToDate(recipe.createdAt);
      const instantString = RecipeUtils.dateToInstantString(createdDate);

      expect(servings).toBe(4);
      expect(totalTime).toBe(45);
      expect(createdDate).toBeInstanceOf(Date);
      expect(instantString).toBe('2024-01-01T10:00:00.000Z');
    });
  });
});