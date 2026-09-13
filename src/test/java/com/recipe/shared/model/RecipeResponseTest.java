package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Parity and serialization tests for RecipeResponse, AuthorDto, and PagedRecipeResponse.
 */
class RecipeResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void testRecipeResponseSerializationAndDeserialization() throws Exception {
        AuthorDto author = AuthorDto.builder()
                .uid("author-123")
                .displayName("Chef Gordon")
                .avatarUrl("https://example.com/avatar.jpg")
                .build();

        RecipeResponse response = RecipeResponse.builder()
                .id("recipe-1")
                .userId("author-123")
                .recipeName("Pasta Primavera")
                .title("Pasta Primavera")
                .description("Fresh garden pasta")
                .ingredients(Arrays.asList("pasta", "vegetables", "olive oil"))
                .instructions(Collections.singletonList("Boil pasta and toss with vegetables"))
                .servings(2)
                .source("manual")
                .createdAt(Instant.parse("2026-01-01T12:00:00Z"))
                .publicRecipe(true)
                .authorDisplayName("Chef Gordon")
                .authorAvatarUrl("https://example.com/avatar.jpg")
                .author(author)
                .likeCount(42)
                .likedByCurrentUser(true)
                .savedByCurrentUser(false)
                .build();

        String json = objectMapper.writeValueAsString(response);
        assertNotNull(json);
        assertTrue(json.contains("\"isPublic\":true"));
        assertTrue(json.contains("\"isLikedByCurrentUser\":true"));
        assertTrue(json.contains("\"isSavedByCurrentUser\":false"));
        assertTrue(json.contains("\"authorDisplayName\":\"Chef Gordon\""));

        RecipeResponse deserialized = objectMapper.readValue(json, RecipeResponse.class);
        assertEquals("recipe-1", deserialized.getId());
        assertEquals("Pasta Primavera", deserialized.getRecipeName());
        assertEquals("Pasta Primavera", deserialized.getTitle());
        assertEquals("Pasta Primavera", deserialized.getEffectiveTitle());
        assertEquals("Chef Gordon", deserialized.getAuthorDisplayName());
        assertNotNull(deserialized.getAuthor());
        assertEquals("author-123", deserialized.getAuthor().getUid());
        assertEquals(42, deserialized.getLikeCount());
        assertTrue(deserialized.getLikedByCurrentUser());
        assertFalse(deserialized.getSavedByCurrentUser());
    }

    @Test
    void testEffectiveTitleFallback() {
        RecipeResponse titleOnly = RecipeResponse.builder().title("Title Only").build();
        assertEquals("Title Only", titleOnly.getEffectiveTitle());

        RecipeResponse recipeNameOnly = RecipeResponse.builder().recipeName("Recipe Name Only").build();
        assertEquals("Recipe Name Only", recipeNameOnly.getEffectiveTitle());

        RecipeResponse both = RecipeResponse.builder().title("Title").recipeName("Recipe Name").build();
        assertEquals("Title", both.getEffectiveTitle());
    }

    @Test
    void testPagedRecipeResponse() throws Exception {
        RecipeResponse item = RecipeResponse.builder()
                .id("rec-1")
                .recipeName("Dish 1")
                .build();

        PagedRecipeResponse paged = PagedRecipeResponse.builder()
                .recipes(Collections.singletonList(item))
                .size(1)
                .totalCount(100)
                .nextPageToken("token-abc")
                .build();

        String json = objectMapper.writeValueAsString(paged);
        PagedRecipeResponse deserialized = objectMapper.readValue(json, PagedRecipeResponse.class);

        assertEquals(1, deserialized.getSize());
        assertEquals(100, deserialized.getTotalCount());
        assertEquals("token-abc", deserialized.getNextPageToken());
        assertEquals(1, deserialized.getRecipes().size());
        assertEquals("Dish 1", deserialized.getRecipes().get(0).getRecipeName());
    }
}
