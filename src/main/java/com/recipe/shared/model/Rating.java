package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Shared model for recipe ratings and reviews.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {

    private String id;
    private String recipeId;
    private String userId;
    private String userName;
    private String userPhotoUrl;

    @Min(1)
    @Max(5)
    private Integer ratingValue;
    private String reviewText;

    private Instant createdAt;
    private Instant updatedAt;
}
