package com.recipe.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocialNotificationTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void testSocialNotificationSerializationWithIsRead() throws Exception {
        SocialNotification notification = SocialNotification.builder()
                .id("n-100")
                .recipientUserId("user-A")
                .actorUserId("user-B")
                .actorName("Chef Bob")
                .type("FOLLOW")
                .message("Chef Bob followed you")
                .read(true)
                .build();

        String json = objectMapper.writeValueAsString(notification);
        assertTrue(json.contains("\"isRead\":true"));

        SocialNotification deserialized = objectMapper.readValue(json, SocialNotification.class);
        assertTrue(deserialized.isRead());
    }
}
