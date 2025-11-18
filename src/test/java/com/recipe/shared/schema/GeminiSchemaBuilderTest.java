package com.recipe.shared.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class GeminiSchemaBuilderTest {

    @Test
    public void simplePropertyShouldEmitLowercaseType() {
        Map<String, Object> s1 = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.STRING, "a string");
        Assertions.assertEquals("string", s1.get("type"));
        Map<String, Object> n = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.NUMBER, "a number");
        Assertions.assertEquals("number", n.get("type"));
        Map<String, Object> i = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.INTEGER, "an integer");
        Assertions.assertEquals("integer", i.get("type"));
        Map<String, Object> b = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.BOOLEAN, "a boolean");
        Assertions.assertEquals("boolean", b.get("type"));
        Map<String, Object> arr = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.ARRAY, "an array");
        Assertions.assertEquals("array", arr.get("type"));
        Map<String, Object> obj = GeminiSchemaBuilder.simpleProperty(GeminiSchemaType.OBJECT, "an object");
        Assertions.assertEquals("object", obj.get("type"));
    }
}
