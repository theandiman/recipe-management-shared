package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Shared model for recipe comments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    private String id;
    private String recipeId;
    private String userId;
    private String userName;
    private String userPhotoUrl;
    private String text;
    
    private Instant createdAt;
    private Instant updatedAt;

    @JsonProperty("isEdited")
    private boolean edited;
}
