package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Shared UserProfile model representing the canonical user profile contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {

  private String uid;
  private String displayName;
  private String bio;
  private String avatarUrl;
  
  @Builder.Default
  private String visibility = "PUBLIC";

  private long publicRecipeCount;
  private long followerCount;
  private long followingCount;

  @JsonProperty("isFollowedByCurrentUser")
  private boolean isFollowedByCurrentUser;

  private String createdAt;
  private String updatedAt;
}
