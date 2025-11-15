package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for NutritionValues model JSON serialization and Map conversion.
 */
class NutritionValuesTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testJsonSerialization() throws Exception {
        // Given
        NutritionValues nutritionValues = NutritionValues.builder()
                .calories(350.0)
                .protein(25.0)
                .carbohydrates(40.0)
                .fat(12.0)
                .fiber(5.0)
                .sodium(800.0)
                .build();

        // When
        String json = objectMapper.writeValueAsString(nutritionValues);
        NutritionValues deserialized = objectMapper.readValue(json, NutritionValues.class);

        // Then
        assertNotNull(json);
        assertEquals(350.0, deserialized.getCalories());
        assertEquals(25.0, deserialized.getProtein());
        assertEquals(40.0, deserialized.getCarbohydrates());
        assertEquals(12.0, deserialized.getFat());
        assertEquals(5.0, deserialized.getFiber());
        assertEquals(800.0, deserialized.getSodium());
    }

    @Test
    void testFromMap() {
        // Given
        Map<String, Object> valuesMap = Map.of(
                "calories", 400,
                "protein", 30.5,
                "carbohydrates", 45.0,
                "fat", 15.0,
                "fiber", 6.0,
                "sodium", 900.0
        );

        // When
        NutritionValues nutritionValues = NutritionValues.fromMap(valuesMap);

        // Then
        assertNotNull(nutritionValues);
        assertEquals(400.0, nutritionValues.getCalories());
        assertEquals(30.5, nutritionValues.getProtein());
        assertEquals(45.0, nutritionValues.getCarbohydrates());
        assertEquals(15.0, nutritionValues.getFat());
        assertEquals(6.0, nutritionValues.getFiber());
        assertEquals(900.0, nutritionValues.getSodium());
    }

    @Test
    void testToMap() {
        // Given
        NutritionValues nutritionValues = NutritionValues.builder()
                .calories(200.0)
                .protein(10.0)
                .build();

        // When
        Map<String, Object> map = nutritionValues.toMap();

        // Then
        assertNotNull(map);
        assertEquals(200.0, map.get("calories"));
        assertEquals(10.0, map.get("protein"));
        assertNull(map.get("carbohydrates")); // Not set
    }

    @Test
    void testFromMap_IntegerValues() {
        // Given - integers instead of doubles
        Map<String, Object> valuesMap = Map.of(
                "calories", 300, // int
                "protein", 20.0  // double
        );

        // When
        NutritionValues nutritionValues = NutritionValues.fromMap(valuesMap);

        // Then
        assertNotNull(nutritionValues);
        assertEquals(300.0, nutritionValues.getCalories()); // Should convert int to double
        assertEquals(20.0, nutritionValues.getProtein());
    }

    @Test
    void testFromMap_NullInput() {
        // When
        NutritionValues nutritionValues = NutritionValues.fromMap(null);

        // Then
        assertNull(nutritionValues);
    }

    @Test
    void testGetDoubleValue_NonNumber() {
        // Given
        Map<String, Object> valuesMap = Map.of(
                "calories", "not-a-number"
        );

        // When
        NutritionValues nutritionValues = NutritionValues.fromMap(valuesMap);

        // Then
        assertNotNull(nutritionValues);
        assertNull(nutritionValues.getCalories()); // Should return null for non-numbers
    }
}