package com.recipe.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class UserProfileTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUserProfileBuilderAndGetters() {
    UserProfile profile = UserProfile.builder()
        .uid("user-123")
        .displayName("Chef Andy")
        .bio("Home cook & recipe enthusiast")
        .avatarUrl("https://example.com/avatar.jpg")
        .visibility("PUBLIC")
        .publicRecipeCount(5L)
        .followerCount(10L)
        .followingCount(2L)
        .isFollowedByCurrentUser(true)
        .createdAt("2026-01-01T00:00:00Z")
        .updatedAt("2026-08-26T00:00:00Z")
        .build();

    assertEquals("user-123", profile.getUid());
    assertEquals("Chef Andy", profile.getDisplayName());
    assertEquals("Home cook & recipe enthusiast", profile.getBio());
    assertEquals("https://example.com/avatar.jpg", profile.getAvatarUrl());
    assertEquals("PUBLIC", profile.getVisibility());
    assertEquals(5L, profile.getPublicRecipeCount());
    assertEquals(10L, profile.getFollowerCount());
    assertEquals(2L, profile.getFollowingCount());
    assertTrue(profile.isFollowedByCurrentUser());
    assertEquals("2026-01-01T00:00:00Z", profile.getCreatedAt());
    assertEquals("2026-08-26T00:00:00Z", profile.getUpdatedAt());
  }

  @Test
  public void testUserProfileJsonSerialization() throws Exception {
    UserProfile profile = UserProfile.builder()
        .uid("user-456")
        .displayName("Jane Doe")
        .bio("Baker")
        .visibility("PRIVATE")
        .followerCount(0L)
        .followingCount(0L)
        .isFollowedByCurrentUser(false)
        .build();

    String json = objectMapper.writeValueAsString(profile);
    assertNotNull(json);
    assertTrue(json.contains("\"uid\":\"user-456\""));
    assertTrue(json.contains("\"visibility\":\"PRIVATE\""));

    UserProfile deserialized = objectMapper.readValue(json, UserProfile.class);
    assertEquals("user-456", deserialized.getUid());
    assertEquals("Jane Doe", deserialized.getDisplayName());
    assertEquals("PRIVATE", deserialized.getVisibility());
    assertFalse(deserialized.isFollowedByCurrentUser());
  }
}
