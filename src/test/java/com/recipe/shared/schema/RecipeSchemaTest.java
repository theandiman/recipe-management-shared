package com.recipe.shared.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class RecipeSchemaTest {

    @Test
    public void testGeneratedSchemaContainsExpectedProperties() {
        JsonSchema schema = RecipeSchema.getSchema();
        Assertions.assertNotNull(schema, "RecipeSchema.getSchema should not return null");
        Map<String, Object> schemaMap = schema.asMap();
        @SuppressWarnings("unchecked")
        Map<String, Object> props = (Map<String, Object>) schemaMap.get("properties");
        Assertions.assertNotNull(props, "Properties should exist in generated schema");
        Assertions.assertTrue(props.containsKey("nutritionalInfo"), "nutritionalInfo should be present");
        Assertions.assertTrue(props.containsKey("servings"), "servings should be present");
        Assertions.assertTrue(props.containsKey("prepTimeMinutes"), "prepTimeMinutes should be present");
        Assertions.assertTrue(props.containsKey("tags"), "tags should be present");
    }
}
