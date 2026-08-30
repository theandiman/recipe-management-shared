package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Shared model for social notifications (like, comment, follow, rating).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialNotification {

    private String id;
    private String recipientUserId;
    private String actorUserId;
    private String actorName;
    private String actorPhotoUrl;
    private String type; // "LIKE", "COMMENT", "FOLLOW", "RATING"
    private String recipeId;
    private String recipeTitle;
    private String message;

    @JsonProperty("isRead")
    private boolean read;

    private Instant createdAt;
}
