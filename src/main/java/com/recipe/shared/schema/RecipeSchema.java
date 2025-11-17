package com.recipe.shared.schema;

import static com.recipe.shared.schema.GeminiSchemaBuilder.*;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.recipe.shared.model.Recipe;

import java.util.List;

/**
 * Recipe schema generator for the shared model. Uses Jackson introspection to build a Gemini-compatible schema.
 */
public final class RecipeSchema {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RecipeSchema() {}

    public static JsonSchema getSchema() {
        // Try to generate dynamically from the shared Recipe class
        try {
            JavaType recipeType = OBJECT_MAPPER.constructType(Recipe.class);
            BeanDescription desc = OBJECT_MAPPER.getSerializationConfig().introspect(recipeType);
            GeminiSchemaBuilder builder = object();
            buildFromBeanDescription(builder, desc, OBJECT_MAPPER, new java.util.HashSet<>());
            return builder.build();
        } catch (Exception e) {
            // If introspection fails, return a manual schema to maintain behavior
        }

        // fallback explicit schema (ensure the AI service has a known schema)
        GeminiSchemaBuilder nutritionValues = object()
            .property("calories", number())
            .property("protein", number())
            .property("carbohydrates", number())
            .property("fat", number())
            .property("fiber", number())
            .property("sodium", number());

        GeminiSchemaBuilder nutritionInfo = object()
            .property("perServing", nutritionValues)
            .property("total", nutritionValues);

        GeminiSchemaBuilder tips = object()
            .property("substitutions", array().items(string()))
            .property("makeAhead", string())
            .property("storage", string())
            .property("reheating", string())
            .property("variations", array().items(string()));

        GeminiSchemaBuilder imageGeneration = object()
            .property("status", string())
            .property("source", string())
            .property("errorMessage", string());

        return object()
            .property("id", string())
            .property("userId", string())
            .property("recipeName", string())
            .property("description", string())
            .property("ingredients", array().items(string()))
            .property("instructions", array().items(string()))
            .property("prepTime", string())
            .property("cookTime", string())
            .property("totalTime", string())
            .property("prepTimeMinutes", integer())
            .property("cookTimeMinutes", integer())
            .property("totalTimeMinutes", integer())
            .property("estimatedTimeMinutes", integer())
            .property("servings", integer())
            .property("nutritionalInfo", nutritionInfo)
            .property("tips", tips)
            .property("imageUrl", string())
            .property("imageGeneration", imageGeneration)
            .property("source", string())
            .property("tags", array().items(string()))
            .property("dietaryRestrictions", array().items(string()))
            .required("recipeName", "ingredients", "instructions", "servings")
            .build();
    }

    private static void buildFromBeanDescription(GeminiSchemaBuilder parentBuilder, BeanDescription desc, ObjectMapper om, java.util.Set<Class<?>> visited) {
        List<BeanPropertyDefinition> props = desc.findProperties();
        for (BeanPropertyDefinition propDef : props) {
            String name = propDef.getName();
            JavaType type = propDef.getPrimaryType();
            GeminiSchemaBuilder propBuilder = translateTypeToGemini(type, om, visited);
            if (propBuilder != null) parentBuilder.property(name, propBuilder);
        }
    }

    private static GeminiSchemaBuilder translateTypeToGemini(JavaType type, ObjectMapper om, java.util.Set<Class<?>> visited) {
        Class<?> raw = type.getRawClass();
        if (raw == String.class) return string();
        if (Number.class.isAssignableFrom(raw) || raw.isPrimitive() && (raw == int.class || raw == long.class || raw == double.class || raw == float.class)) {
            if (raw == Integer.class || raw == Long.class || raw == Short.class || raw == Byte.class || raw == int.class || raw == long.class || raw == short.class || raw == byte.class) return integer();
            return number();
        }
        if (raw == Boolean.class || raw == boolean.class) return bool();
        if (java.util.Collection.class.isAssignableFrom(raw) || raw.isArray()) {
            JavaType content = type.getContentType();
            GeminiSchemaBuilder items = content == null ? string() : translateTypeToGemini(content, om, visited);
            return array().items(items == null ? string() : items);
        }
        if (java.util.Map.class.isAssignableFrom(raw)) {
            return object();
        }
        if (!visited.add(raw)) {
            return object();
        }
        try {
            JavaType jt = om.constructType(raw);
            BeanDescription bd = om.getSerializationConfig().introspect(jt);
            GeminiSchemaBuilder nested = object();
            buildFromBeanDescription(nested, bd, om, visited);
            return nested;
        } catch (Exception e) {
            return object();
        }
    }
}
