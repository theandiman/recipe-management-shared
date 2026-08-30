package com.recipe.shared.model;

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
    private List<String> substitutions;

    @JsonProperty("makeAhead")
    private String makeAhead;

    @JsonProperty("storage")
    private String storage;

    @JsonProperty("reheating")
    private String reheating;

    @JsonProperty("variations")
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

        if (tipsMap.get("substitutions") instanceof List) {
            builder.substitutions((List<String>) tipsMap.get("substitutions"));
        }
        if (tipsMap.get("variations") instanceof List) {
            builder.variations((List<String>) tipsMap.get("variations"));
        }

        builder.storage(extractString(tipsMap.get("storage")));
        builder.makeAhead(extractString(tipsMap.get("makeAhead")));
        builder.reheating(extractString(tipsMap.get("reheating")));

        return builder.build();
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