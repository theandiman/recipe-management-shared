package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for RecipeTips model JSON serialization and Map conversion.
 */
class RecipeTipsTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testJsonSerialization() throws Exception {
        // Given
        RecipeTips recipeTips = RecipeTips.builder()
                .substitutions(Arrays.asList("Use almond milk instead of regular milk", "Substitute gluten-free flour"))
                .makeAhead("Prepare the dough up to 24 hours in advance and refrigerate")
                .storage("Store in an airtight container in the refrigerator for up to 3 days")
                .reheating("Reheat in microwave for 1-2 minutes or in oven at 350°F for 10 minutes")
                .variations(Arrays.asList("Add chocolate chips for extra sweetness", "Make mini muffins instead"))
                .build();

        // When
        String json = objectMapper.writeValueAsString(recipeTips);
        RecipeTips deserialized = objectMapper.readValue(json, RecipeTips.class);

        // Then
        assertNotNull(json);
        assertEquals(recipeTips.getSubstitutions(), deserialized.getSubstitutions());
        assertEquals(recipeTips.getMakeAhead(), deserialized.getMakeAhead());
        assertEquals(recipeTips.getStorage(), deserialized.getStorage());
        assertEquals(recipeTips.getReheating(), deserialized.getReheating());
        assertEquals(recipeTips.getVariations(), deserialized.getVariations());
    }

    @Test
    void testFromMap() {
        // Given
        List<String> substitutions = Arrays.asList("Use soy milk", "Try coconut oil");
        List<String> variations = Arrays.asList("Add nuts", "Make it vegan");
        List<String> storage = List.of("Refrigerate up to 3 days");
        List<String> makeAhead = List.of("Prepare sauce ahead");
        String reheating = "Reheat gently";

        Map<String, Object> tipsMap = Map.of(
                "substitutions", substitutions,
                "variations", variations,
                "storage", storage,
                "makeAhead", makeAhead,
                "reheating", reheating
        );

        // When
        RecipeTips recipeTips = RecipeTips.fromMap(tipsMap);

        // Then
        assertNotNull(recipeTips);
        assertEquals(substitutions, recipeTips.getSubstitutions());
        assertEquals(variations, recipeTips.getVariations());
        assertEquals("Refrigerate up to 3 days", recipeTips.getStorage());
        assertEquals("Prepare sauce ahead", recipeTips.getMakeAhead());
        assertEquals("Reheat gently", recipeTips.getReheating());
    }

    @Test
    void testToMap() {
        // Given
        RecipeTips recipeTips = RecipeTips.builder()
                .substitutions(Arrays.asList("Use olive oil", "Try honey"))
                .variations(Arrays.asList("Spicy version", "Sweet version"))
                .storage("Store in fridge")
                .makeAhead("Prep ingredients")
                .reheating("Microwave for 1 min")
                .build();

        // When
        Map<String, List<String>> map = recipeTips.toMap();

        // Then
        assertNotNull(map);
        assertEquals(recipeTips.getSubstitutions(), map.get("substitutions"));
        assertEquals(recipeTips.getVariations(), map.get("variations"));
        assertEquals(List.of("Store in fridge"), map.get("storage"));
        assertEquals(List.of("Prep ingredients"), map.get("makeAhead"));
        assertEquals(List.of("Microwave for 1 min"), map.get("reheating"));
    }

    @Test
    void testFromMap_NullInput() {
        // When
        RecipeTips recipeTips = RecipeTips.fromMap(null);

        // Then
        assertNull(recipeTips);
    }

    @Test
    void testBuilderPattern() {
        // Test that Lombok @Builder works correctly
        RecipeTips recipeTips = RecipeTips.builder()
                .makeAhead("Can be made ahead")
                .storage("Refrigerate")
                .build();

        assertEquals("Can be made ahead", recipeTips.getMakeAhead());
        assertEquals("Refrigerate", recipeTips.getStorage());
    }
}