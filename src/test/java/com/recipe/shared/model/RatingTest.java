package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void testRatingSerialization() throws Exception {
        Rating rating = Rating.builder()
                .id("r-1")
                .recipeId("recipe-1")
                .userId("user-1")
                .userName("Jane")
                .ratingValue(5)
                .reviewText("Outstanding pasta!")
                .build();

        String json = objectMapper.writeValueAsString(rating);
        Rating deserialized = objectMapper.readValue(json, Rating.class);

        assertEquals("r-1", deserialized.getId());
        assertEquals(5, deserialized.getRatingValue());
        assertEquals("Outstanding pasta!", deserialized.getReviewText());
    }
}
