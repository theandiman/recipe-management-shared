package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Nutritional information for a recipe, including both per-serving and total values.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalInfo {

    @JsonProperty("perServing")
    private NutritionValues perServing;

    @JsonProperty("total")
    private NutritionValues total;

    /**
     * Creates a NutritionalInfo from a Map structure (for storage service compatibility).
     */
    public static NutritionalInfo fromMap(Map<String, Object> nutritionMap) {
        if (nutritionMap == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> perServingMap = (Map<String, Object>) nutritionMap.get("perServing");
        @SuppressWarnings("unchecked")
        Map<String, Object> totalMap = (Map<String, Object>) nutritionMap.get("total");

        return NutritionalInfo.builder()
                .perServing(perServingMap != null ? NutritionValues.fromMap(perServingMap) : null)
                .total(totalMap != null ? NutritionValues.fromMap(totalMap) : null)
                .build();
    }

    /**
     * Converts to Map structure (for storage service compatibility).
     */
    public Map<String, Object> toMap() {
        return Map.of(
                "perServing", perServing != null ? perServing.toMap() : null,
                "total", total != null ? total.toMap() : null
        );
    }
}