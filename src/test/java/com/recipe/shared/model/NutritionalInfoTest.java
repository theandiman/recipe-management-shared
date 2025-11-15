package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for NutritionalInfo model JSON serialization and Map conversion.
 */
class NutritionalInfoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testJsonSerialization() throws Exception {
        // Given
        NutritionValues perServing = NutritionValues.builder()
                .calories(250.0)
                .protein(15.0)
                .carbohydrates(30.0)
                .fat(8.0)
                .build();

        NutritionValues total = NutritionValues.builder()
                .calories(1000.0)
                .protein(60.0)
                .carbohydrates(120.0)
                .fat(32.0)
                .build();

        NutritionalInfo nutritionalInfo = NutritionalInfo.builder()
                .perServing(perServing)
                .total(total)
                .build();

        // When
        String json = objectMapper.writeValueAsString(nutritionalInfo);
        NutritionalInfo deserialized = objectMapper.readValue(json, NutritionalInfo.class);

        // Then
        assertNotNull(json);
        assertEquals(250.0, deserialized.getPerServing().getCalories());
        assertEquals(15.0, deserialized.getPerServing().getProtein());
        assertEquals(1000.0, deserialized.getTotal().getCalories());
        assertEquals(60.0, deserialized.getTotal().getProtein());
    }

    @Test
    void testFromMap() {
        // Given
        Map<String, Object> perServingMap = Map.of(
                "calories", 300.0,
                "protein", 20.0,
                "carbohydrates", 35.0,
                "fat", 10.0
        );

        Map<String, Object> totalMap = Map.of(
                "calories", 1200.0,
                "protein", 80.0,
                "carbohydrates", 140.0,
                "fat", 40.0
        );

        Map<String, Object> nutritionMap = Map.of(
                "perServing", perServingMap,
                "total", totalMap
        );

        // When
        NutritionalInfo nutritionalInfo = NutritionalInfo.fromMap(nutritionMap);

        // Then
        assertNotNull(nutritionalInfo);
        assertNotNull(nutritionalInfo.getPerServing());
        assertEquals(300.0, nutritionalInfo.getPerServing().getCalories());
        assertEquals(20.0, nutritionalInfo.getPerServing().getProtein());
        assertNotNull(nutritionalInfo.getTotal());
        assertEquals(1200.0, nutritionalInfo.getTotal().getCalories());
        assertEquals(80.0, nutritionalInfo.getTotal().getProtein());
    }

    @Test
    void testToMap() {
        // Given
        NutritionValues perServing = NutritionValues.builder()
                .calories(250.0)
                .protein(15.0)
                .build();

        NutritionalInfo nutritionalInfo = NutritionalInfo.builder()
                .perServing(perServing)
                .build();

        // When
        Map<String, Object> map = nutritionalInfo.toMap();

        // Then
        assertNotNull(map);
        assertTrue(map.containsKey("perServing"));
        assertTrue(map.containsKey("total"));

        @SuppressWarnings("unchecked")
        Map<String, Object> perServingMap = (Map<String, Object>) map.get("perServing");
        assertNotNull(perServingMap);
        assertEquals(250.0, perServingMap.get("calories"));
        assertEquals(15.0, perServingMap.get("protein"));
    }

    @Test
    void testFromMap_NullInput() {
        // When
        NutritionalInfo nutritionalInfo = NutritionalInfo.fromMap(null);

        // Then
        assertNull(nutritionalInfo);
    }
}