package com.recipe.shared.schema;

import static com.recipe.shared.schema.GeminiSchemaBuilder.*;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.recipe.shared.model.Recipe;

import java.util.List;

/**
 * Recipe schema generator for the shared model. Uses Jackson introspection to build a Gemini-compatible schema.
 */
public final class RecipeSchema {

    private static final Logger log = LoggerFactory.getLogger(RecipeSchema.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Cache for schema builders to handle repeated types efficiently
    private static final java.util.Map<String, GeminiSchemaBuilder> schemaCache = new java.util.concurrent.ConcurrentHashMap<>();

    private RecipeSchema() {}

    public static JsonSchema getSchema() {
        // Try to generate dynamically from the shared Recipe class
        try {
            JavaType recipeType = OBJECT_MAPPER.constructType(Recipe.class);
            BeanDescription desc = OBJECT_MAPPER.getSerializationConfig().introspect(recipeType);
            GeminiSchemaBuilder builder = object();
            buildFromBeanDescription(builder, desc, OBJECT_MAPPER);
            return builder.build();
        } catch (Exception e) {
            // If introspection fails, log and return a manual schema to maintain behavior
            log.warn("Failed to introspect Recipe class to build dynamic schema; using fallback manual schema", e);
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
            /* imageGeneration intentionally omitted from the schema since images are generated separately via the dedicated endpoint */
            .property("source", string())
            .property("tags", array().items(string()))
            .property("dietaryRestrictions", array().items(string()))
            .required("recipeName", "ingredients", "instructions", "servings")
            .build();
    }

    private static void buildFromBeanDescription(GeminiSchemaBuilder parentBuilder, BeanDescription desc, ObjectMapper om) {
        List<BeanPropertyDefinition> props = desc.findProperties();
        for (BeanPropertyDefinition propDef : props) {
            String name = propDef.getName();
            JavaType type = propDef.getPrimaryType();
            // Skip imageGeneration entirely in the schema because images are generated separately.
            if ("imageGeneration".equals(name)) continue;
            GeminiSchemaBuilder propBuilder = translateTypeToGemini(name, type, om);
            if (propBuilder != null) parentBuilder.property(name, propBuilder);
        }
    }

    private static GeminiSchemaBuilder translateTypeToGemini(String propName, JavaType type, ObjectMapper om) {
        if ("imageGeneration".equals(propName)) return null;
        Class<?> raw = type.getRawClass();
        // No special-casing here; callers can skip properties by name when generating the schema
        if (raw == String.class) return string();
        if (Number.class.isAssignableFrom(raw) || raw.isPrimitive() && (raw == int.class || raw == long.class || raw == double.class || raw == float.class || raw == short.class || raw == byte.class)) {
            // Treat integral numeric types as integer schema types; all others (Double/Float) as number
            java.util.Set<Class<?>> integerLike = java.util.Set.of(Integer.class, Long.class, Short.class, Byte.class, int.class, long.class, short.class, byte.class);
            if (integerLike.contains(raw)) return integer();
            return number();
        }
        if (raw == Boolean.class || raw == boolean.class) return bool();
        if (java.util.Collection.class.isAssignableFrom(raw) || raw.isArray()) {
            JavaType content = type.getContentType();
            GeminiSchemaBuilder items = content == null ? string() : translateTypeToGemini(null, content, om);
            return array().items(items == null ? string() : items);
        }
        if (java.util.Map.class.isAssignableFrom(raw)) {
            // For unspecified map types, default to an object schema with a generic string property to satisfy
            // the Gemini API which expects non-empty properties for object types.
            return object().property("value", string());
        }
        // Check cache first
        String className = raw.getName();
        GeminiSchemaBuilder cached = schemaCache.get(className);
        if (cached != null) {
            return cached;
        }
        try {
            JavaType jt = om.constructType(raw);
            BeanDescription bd = om.getSerializationConfig().introspect(jt);
            GeminiSchemaBuilder nested = object();
            buildFromBeanDescription(nested, bd, om);
            schemaCache.put(className, nested);
            return nested;
        } catch (Exception e) {
            // Log nested type introspection failures for easier debugging and fall back to an object schema
            log.debug("Failed to introspect nested type '{}' for schema generation; falling back to object", raw == null ? "null" : raw.getName(), e);
            return object();
        }
    }
}
