package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Enriched Recipe Response DTO returned by storage service endpoints.
 * Provides both recipeName and title aliases for backward compatibility,
 * and encapsulates social and author metadata.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeResponse {

    private String id;
    private String userId;

    @JsonProperty("recipeName")
    private String recipeName;

    @JsonProperty("title")
    private String title;

    private String description;
    private List<String> ingredients;
    private List<String> instructions;

    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer totalTimeMinutes;

    private String prepTime;
    private String cookTime;
    private String totalTime;

    private Integer servings;
    private NutritionalInfo nutritionalInfo;
    private RecipeTips tips;

    private String imageUrl;
    private String source;
    private Instant createdAt;
    private Instant updatedAt;

    private List<String> tags;
    private List<String> dietaryRestrictions;

    @JsonProperty("isPublic")
    private boolean publicRecipe;

    private Double averageRating;
    private Integer ratingCount;

    private Map<String, Object> imageGeneration;

    // Author metadata
    private String authorDisplayName;
    private String authorAvatarUrl;
    private AuthorDto author;

    // Social user interactions
    private Integer likeCount;

    @JsonProperty("isLikedByCurrentUser")
    private Boolean likedByCurrentUser;

    @JsonProperty("isSavedByCurrentUser")
    private Boolean savedByCurrentUser;

    /**
     * Helper to resolve the effective display title (falls back between title and recipeName).
     */
    @JsonIgnore
    public String getEffectiveTitle() {
        if (title != null && !title.trim().isEmpty()) {
            return title;
        }
        return recipeName;
    }
}
