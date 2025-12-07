/**
 * Shared Recipe model used across all recipe management services.
 * This model provides a consistent interface for recipe data exchange
 * between frontend, AI service, and storage service.
 */

export interface Recipe {
  // Core recipe information
  id?: string; // Unique identifier (Firestore document ID for storage)
  userId?: string; // Firebase user ID who owns the recipe

  recipeName: string; // Consistent naming across services

  description?: string;

  // Recipe content
  ingredients: string[]; // List of ingredient strings with quantities
  instructions: string[]; // Step-by-step cooking instructions

  // Timing information
  prepTimeMinutes?: number; // Preparation time in minutes
  cookTimeMinutes?: number; // Cooking time in minutes
  totalTimeMinutes?: number; // Total estimated time in minutes

  // Human-readable time strings (for AI service compatibility)
  prepTime?: string; // e.g., "15 minutes"
  cookTime?: string; // e.g., "20 minutes"
  totalTime?: string; // e.g., "35 minutes"

  // Servings
  servings: number; // Number of servings

  // Nutritional information - structured for frontend, flexible for backend
  nutritionalInfo?: NutritionalInfo;

  // Recipe tips and additional information
  tips?: RecipeTips;

  // Media
  imageUrl?: string;

  // Metadata
  source: 'ai-generated' | 'manual' | string; // Source of the recipe
  createdAt?: string | Date;
  updatedAt?: string | Date;

  // Categorization
  tags?: string[];
  dietaryRestrictions?: string[];

  isPublic?: boolean;

  // AI-specific fields (optional, for AI service compatibility)
  imageGeneration?: Record<string, any>; // AI image generation metadata
}

export interface NutritionalInfo {
  perServing?: NutritionValues;
  total?: NutritionValues;
}

export interface NutritionValues {
  calories?: number;
  protein?: number;
  carbohydrates?: number;
  fat?: number;
  fiber?: number;
  sodium?: number;
}

export interface RecipeTips {
  substitutions?: string[];
  makeAhead?: string;
  storage?: string;
  reheating?: string;
  variations?: string[];
}

/**
 * Utility functions for working with shared recipe models
 */
export class RecipeUtils {
  /**
   * Converts servings from string to number if needed.
   */
  static getServingsAsNumber(servings: string | number): number {
    if (typeof servings === 'string') {
      return parseInt(servings, 10);
    }
    return servings;
  }

  /**
   * Gets total time in minutes, calculating if not explicitly set.
   */
  static getCalculatedTotalTimeMinutes(recipe: Recipe): number | undefined {
    if (recipe.totalTimeMinutes) {
      return recipe.totalTimeMinutes;
    }
    if (recipe.prepTimeMinutes && recipe.cookTimeMinutes) {
      return recipe.prepTimeMinutes + recipe.cookTimeMinutes;
    }
    return undefined;
  }

  /**
   * Converts Java Instant to JavaScript Date
   */
  static instantToDate(instant: string): Date {
    return new Date(instant);
  }

  /**
   * Converts JavaScript Date to ISO string for Java Instant
   */
  static dateToInstantString(date: Date): string {
    return date.toISOString();
  }
}