package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Recipe model JSON serialization and utility methods.
 */
class RecipeTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void testJsonSerialization() throws Exception {
        // Given
        Recipe recipe = Recipe.builder()
                .id("test-recipe-123")
                .userId("user-456")
                .recipeName("Test Recipe")
                .description("A test recipe")
                .ingredients(Arrays.asList("1 cup flour", "2 eggs", "1/2 cup milk"))
                .instructions(Arrays.asList("Mix ingredients", "Bake at 350°F for 30 minutes"))
                .prepTimeMinutes(15)
                .cookTimeMinutes(30)
                .totalTimeMinutes(45)
                .servings(4)
                .source("manual")
                .createdAt(Instant.parse("2024-01-01T10:00:00Z"))
                .tags(Arrays.asList("breakfast", "easy"))
                .publicRecipe(true)
                .build();

        // When
        String json = objectMapper.writeValueAsString(recipe);
        Recipe deserialized = objectMapper.readValue(json, Recipe.class);

        // Then
        assertNotNull(json);
        assertEquals(recipe.getId(), deserialized.getId());
        assertEquals(recipe.getRecipeName(), deserialized.getRecipeName());
        assertEquals(recipe.getIngredients(), deserialized.getIngredients());
        assertEquals(recipe.getPrepTimeMinutes(), deserialized.getPrepTimeMinutes());
        assertEquals(recipe.getCookTimeMinutes(), deserialized.getCookTimeMinutes());
        assertEquals(recipe.getTotalTimeMinutes(), deserialized.getTotalTimeMinutes());
        assertEquals(recipe.getServings(), deserialized.getServings());
        assertEquals(recipe.getSource(), deserialized.getSource());
        assertEquals(recipe.getTags(), deserialized.getTags());
        assertEquals(recipe.isPublicRecipe(), deserialized.isPublicRecipe());
    }

    @Test
    void testGetServingsAsInt() {
        // Given
        Recipe recipe = Recipe.builder().servings(4).build();

        // When
        Integer servings = recipe.getServingsAsInt();

        // Then
        assertEquals(4, servings);
    }

    @Test
    void testGetCalculatedTotalTimeMinutes_ExplicitTotal() {
        // Given
        Recipe recipe = Recipe.builder()
                .prepTimeMinutes(15)
                .cookTimeMinutes(30)
                .totalTimeMinutes(50) // Explicit total
                .build();

        // When
        Integer total = recipe.getCalculatedTotalTimeMinutes();

        // Then
        assertEquals(50, total); // Should return explicit total
    }

    @Test
    void testGetCalculatedTotalTimeMinutes_Calculated() {
        // Given
        Recipe recipe = Recipe.builder()
                .prepTimeMinutes(15)
                .cookTimeMinutes(30)
                // No explicit total
                .build();

        // When
        Integer total = recipe.getCalculatedTotalTimeMinutes();

        // Then
        assertEquals(45, total); // Should calculate prep + cook
    }

    @Test
    void testGetCalculatedTotalTimeMinutes_NullValues() {
        // Given
        Recipe recipe = Recipe.builder().build(); // No timing values

        // When
        Integer total = recipe.getCalculatedTotalTimeMinutes();

        // Then
        assertNull(total);
    }

    @Test
    void testBuilderPattern() {
        // Test that Lombok @Builder works correctly
        Recipe recipe = Recipe.builder()
                .recipeName("Builder Test")
                .servings(2)
                .build();

        assertEquals("Builder Test", recipe.getRecipeName());
        assertEquals(2, recipe.getServings());
    }
}