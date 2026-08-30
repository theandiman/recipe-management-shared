package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void testCommentSerializationAndDeserialization() throws Exception {
        Instant now = Instant.now();
        Comment comment = Comment.builder()
                .id("comment-123")
                .recipeId("recipe-456")
                .userId("user-789")
                .userName("Chef Andy")
                .userPhotoUrl("https://example.com/avatar.jpg")
                .text("Delicious recipe!")
                .createdAt(now)
                .updatedAt(now)
                .edited(true)
                .build();

        String json = objectMapper.writeValueAsString(comment);
        assertTrue(json.contains("\"isEdited\":true"));

        Comment deserialized = objectMapper.readValue(json, Comment.class);
        assertEquals("comment-123", deserialized.getId());
        assertEquals("Delicious recipe!", deserialized.getText());
        assertTrue(deserialized.isEdited());
    }

    @Test
    void testCommentIgnoresUnknownProperties() throws Exception {
        String json = "{\"id\":\"c1\",\"text\":\"Great!\",\"unknownField\":\"value\"}";
        Comment comment = objectMapper.readValue(json, Comment.class);
        assertEquals("c1", comment.getId());
        assertEquals("Great!", comment.getText());
    }
}
