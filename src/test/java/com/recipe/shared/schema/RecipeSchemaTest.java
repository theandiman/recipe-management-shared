package com.recipe.shared.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import org.junit.jupiter.api.Assertions;

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

        // Verify numeric types for specific fields
        @SuppressWarnings("unchecked")
        Map<String, Object> servings = (Map<String, Object>) props.get("servings");
        Assertions.assertNotNull(servings, "servings property must exist");
        Assertions.assertEquals("integer", servings.get("type"), "servings should be an integer type");

        @SuppressWarnings("unchecked")
        Map<String, Object> prepTimeMinutes = (Map<String, Object>) props.get("prepTimeMinutes");
        Assertions.assertNotNull(prepTimeMinutes, "prepTimeMinutes property must exist");
        Assertions.assertEquals("integer", prepTimeMinutes.get("type"), "prepTimeMinutes should be an integer type");

        // Verify nested nutritional info types
        @SuppressWarnings("unchecked")
        Map<String, Object> nutritionalInfo = (Map<String, Object>) props.get("nutritionalInfo");
        Assertions.assertNotNull(nutritionalInfo, "nutritionalInfo must exist");
        @SuppressWarnings("unchecked")
        Map<String, Object> nutritionalProps = (Map<String, Object>) nutritionalInfo.get("properties");
        Assertions.assertNotNull(nutritionalProps, "nutritionalInfo should have properties");
        @SuppressWarnings("unchecked")
        Map<String, Object> perServing = (Map<String, Object>) nutritionalProps.get("perServing");
        Assertions.assertNotNull(perServing, "perServing should be present in nutritionalInfo");
        @SuppressWarnings("unchecked")
        Map<String, Object> perServingProps = (Map<String, Object>) perServing.get("properties");
        Assertions.assertNotNull(perServingProps, "perServing should have properties");
        @SuppressWarnings("unchecked")
        Map<String, Object> calories = (Map<String, Object>) perServingProps.get("calories");
        Assertions.assertNotNull(calories, "calories should be present in perServing");
        Assertions.assertEquals("number", calories.get("type"), "calories should be a number type");
    }
}
