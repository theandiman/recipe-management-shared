package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Shared Recipe model used across all recipe management services.
 * This model provides a consistent interface for recipe data exchange
 * between frontend, AI service, and storage service.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    // Core recipe information
    private String id; // Unique identifier (Firestore document ID for storage)
    private String userId; // Firebase user ID who owns the recipe

    @JsonProperty("recipeName")
    private String recipeName; // Consistent naming across services

    private String description;

    // Recipe content
    private List<String> ingredients; // List of ingredient strings with quantities
    private List<String> instructions; // Step-by-step cooking instructions

    // Timing information
    private Integer prepTimeMinutes; // Preparation time in minutes
    private Integer cookTimeMinutes; // Cooking time in minutes
    private Integer totalTimeMinutes; // Total estimated time in minutes

    // Human-readable time strings (for AI service compatibility)
    private String prepTime; // e.g., "15 minutes"
    private String cookTime; // e.g., "20 minutes"
    private String totalTime; // e.g., "35 minutes"

    // Servings
    private Integer servings; // Number of servings

    // Nutritional information - structured for frontend, flexible for backend
    private NutritionalInfo nutritionalInfo;

    // Recipe tips and additional information
    private RecipeTips tips;

    // Media
    private String imageUrl;

    // Metadata
    private String source; // "ai-generated", "manual", etc.
    private Instant createdAt;
    private Instant updatedAt;

    // Categorization
    private List<String> tags;
    private List<String> dietaryRestrictions;

    @JsonProperty("isPublic")
    private boolean isPublic; // Whether recipe is publicly visible to other users

    // AI-specific fields (optional, for AI service compatibility)
    private Map<String, Object> imageGeneration; // AI image generation metadata

    /**
     * Converts servings from string to integer if needed.
     * Useful for frontend compatibility where servings might be a string.
     */
    @JsonIgnore
    public Integer getServingsAsInt() {
        if (servings != null) {
            return servings;
        }
        return null;
    }

    /**
     * Gets total time in minutes, calculating if not explicitly set.
     */
    @JsonIgnore
    public Integer getCalculatedTotalTimeMinutes() {
        if (totalTimeMinutes != null) {
            return totalTimeMinutes;
        }
        if (prepTimeMinutes != null && cookTimeMinutes != null) {
            return prepTimeMinutes + cookTimeMinutes;
        }
        return null;
    }
}