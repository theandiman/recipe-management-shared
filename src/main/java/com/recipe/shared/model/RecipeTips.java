package com.recipe.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * The storage service uses Map<String, List<String>> for tips.
     */
    public static RecipeTips fromMap(Map<String, List<String>> tipsMap) {
        if (tipsMap == null) {
            return null;
        }

        return RecipeTips.builder()
                .substitutions(tipsMap.get("substitutions"))
                .variations(tipsMap.get("variations"))
                .build();
    }

    /**
     * Converts to Map structure (for storage service compatibility).
     * Note: This is a simplified conversion - complex tips with makeAhead, storage,
     * and reheating would need additional mapping logic.
     */
    public Map<String, List<String>> toMap() {
        return Map.of(
                "substitutions", substitutions,
                "variations", variations
        );
    }
}