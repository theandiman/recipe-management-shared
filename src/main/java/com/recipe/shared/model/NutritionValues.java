package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Nutritional values for a recipe or serving.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionValues {

    @JsonProperty("calories")
    private Double calories;

    @JsonProperty("protein")
    private Double protein;

    @JsonProperty("carbohydrates")
    private Double carbohydrates;

    @JsonProperty("fat")
    private Double fat;

    @JsonProperty("fiber")
    private Double fiber;

    @JsonProperty("sodium")
    private Double sodium;

    /**
     * Creates NutritionValues from a Map structure (for storage service compatibility).
     */
    public static NutritionValues fromMap(Map<String, Object> valuesMap) {
        if (valuesMap == null) {
            return null;
        }

        return NutritionValues.builder()
                .calories(getDoubleValue(valuesMap.get("calories")))
                .protein(getDoubleValue(valuesMap.get("protein")))
                .carbohydrates(getDoubleValue(valuesMap.get("carbohydrates")))
                .fat(getDoubleValue(valuesMap.get("fat")))
                .fiber(getDoubleValue(valuesMap.get("fiber")))
                .sodium(getDoubleValue(valuesMap.get("sodium")))
                .build();
    }

    /**
     * Converts to Map structure (for storage service compatibility).
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new java.util.HashMap<>();
        if (calories != null) map.put("calories", calories);
        if (protein != null) map.put("protein", protein);
        if (carbohydrates != null) map.put("carbohydrates", carbohydrates);
        if (fat != null) map.put("fat", fat);
        if (fiber != null) map.put("fiber", fiber);
        if (sodium != null) map.put("sodium", sodium);
        return map;
    }

    private static Double getDoubleValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }
}