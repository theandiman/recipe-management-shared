package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Recipe tips including substitutions, make-ahead instructions, and storage advice.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeTips {

    @JsonProperty("substitutions")
    @JsonAlias({"ingredientSubstitutions", "ingredient_substitutions"})
    private List<String> substitutions;

    @JsonProperty("makeAhead")
    @JsonAlias({"makeAheadTips", "make_ahead", "make_ahead_tips"})
    private String makeAhead;

    @JsonProperty("storage")
    @JsonAlias({"storageInstructions", "storage_instructions"})
    private String storage;

    @JsonProperty("reheating")
    @JsonAlias({"reheatingInstructions", "reheating_instructions"})
    private String reheating;

    @JsonProperty("variations")
    @JsonAlias({"recipeVariations", "recipe_variations"})
    private List<String> variations;

    /**
     * Creates RecipeTips from a Map structure (for storage service compatibility).
     * The storage service uses Map<String, List<String>> or Map<String, Object> for tips.
     */
    @SuppressWarnings("unchecked")
    public static RecipeTips fromMap(Map<String, ?> tipsMap) {
        if (tipsMap == null) {
            return null;
        }

        RecipeTipsBuilder builder = RecipeTips.builder();

        Object subs = getFirstNonNull(tipsMap, "substitutions", "ingredientSubstitutions", "ingredient_substitutions");
        if (subs instanceof List<?> list) {
            builder.substitutions((List<String>) list.stream().filter(o -> o != null).map(Object::toString).toList());
        }

        Object vars = getFirstNonNull(tipsMap, "variations", "recipeVariations", "recipe_variations");
        if (vars instanceof List<?> list) {
            builder.variations((List<String>) list.stream().filter(o -> o != null).map(Object::toString).toList());
        }

        Object stor = getFirstNonNull(tipsMap, "storage", "storageInstructions", "storage_instructions");
        builder.storage(extractString(stor));

        Object make = getFirstNonNull(tipsMap, "makeAhead", "makeAheadTips", "make_ahead", "make_ahead_tips");
        builder.makeAhead(extractString(make));

        Object reht = getFirstNonNull(tipsMap, "reheating", "reheatingInstructions", "reheating_instructions");
        builder.reheating(extractString(reht));

        return builder.build();
    }

    private static Object getFirstNonNull(Map<String, ?> map, String... keys) {
        if (map == null) return null;
        for (String key : keys) {
            Object val = map.get(key);
            if (val != null) return val;
        }
        return null;
    }

    private static String extractString(Object obj) {
        if (obj == null) return null;
        if (obj instanceof String str) return str;
        if (obj instanceof List<?> list && !list.isEmpty() && list.get(0) != null) {
            return list.get(0).toString();
        }
        return null;
    }

    /**
     * Converts to Map structure (for storage service compatibility).
     */
    public Map<String, List<String>> toMap() {
        Map<String, List<String>> map = new HashMap<>();
        if (substitutions != null && !substitutions.isEmpty()) {
            map.put("substitutions", substitutions);
        }
        if (variations != null && !variations.isEmpty()) {
            map.put("variations", variations);
        }
        if (storage != null && !storage.isBlank()) {
            map.put("storage", List.of(storage));
        }
        if (makeAhead != null && !makeAhead.isBlank()) {
            map.put("makeAhead", List.of(makeAhead));
        }
        if (reheating != null && !reheating.isBlank()) {
            map.put("reheating", List.of(reheating));
        }
        return map;
    }
}