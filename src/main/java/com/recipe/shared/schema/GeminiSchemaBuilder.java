package com.recipe.shared.schema;

import java.util.*;

/**
 * Fluent builder for creating Gemini API JSON schemas.
 * Copied into shared module so other services can reuse it.
 */
public class GeminiSchemaBuilder {

    private final Map<String, Object> schema = new LinkedHashMap<>();

    private GeminiSchemaBuilder(GeminiSchemaType type) {
        schema.put("type", type.name());
    }

    public static GeminiSchemaBuilder string() {
        return new GeminiSchemaBuilder(GeminiSchemaType.STRING);
    }

    public static GeminiSchemaBuilder number() {
        return new GeminiSchemaBuilder(GeminiSchemaType.NUMBER);
    }

    public static GeminiSchemaBuilder integer() {
        return new GeminiSchemaBuilder(GeminiSchemaType.INTEGER);
    }

    public static GeminiSchemaBuilder bool() {
        return new GeminiSchemaBuilder(GeminiSchemaType.BOOLEAN);
    }

    public static GeminiSchemaBuilder array() {
        return new GeminiSchemaBuilder(GeminiSchemaType.ARRAY);
    }

    public static GeminiSchemaBuilder object() {
        return new GeminiSchemaBuilder(GeminiSchemaType.OBJECT);
    }

    public GeminiSchemaBuilder description(String description) {
        schema.put("description", description);
        return this;
    }

    public GeminiSchemaBuilder items(GeminiSchemaBuilder itemsBuilder) {
        schema.put("items", itemsBuilder.buildAsMap());
        return this;
    }

    public GeminiSchemaBuilder items(Map<String, Object> itemsSchema) {
        schema.put("items", itemsSchema);
        return this;
    }

    public GeminiSchemaBuilder properties(Map<String, Map<String, Object>> properties) {
        schema.put("properties", properties);
        return this;
    }

    public GeminiSchemaBuilder property(String name, GeminiSchemaBuilder propertyBuilder) {
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> props = (Map<String, Map<String, Object>>) schema.computeIfAbsent("properties", k -> new LinkedHashMap<>());
        props.put(name, propertyBuilder.buildAsMap());
        return this;
    }

    public GeminiSchemaBuilder property(String name, Map<String, Object> propertySchema) {
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> props = (Map<String, Map<String, Object>>) schema.computeIfAbsent("properties", k -> new LinkedHashMap<>());
        props.put(name, propertySchema);
        return this;
    }

    public GeminiSchemaBuilder required(String... fieldNames) {
        schema.put("required", List.of(fieldNames));
        return this;
    }

    public GeminiSchemaBuilder required(List<String> fieldNames) {
        schema.put("required", List.copyOf(fieldNames));
        return this;
    }

    public JsonSchema build() {
        return new JsonSchema(schema);
    }

    /**
     * Builds and returns the schema as a raw map for backwards compatibility.
     * @deprecated Use {@link #build()} instead to get a JsonSchema object.
     */
    @Deprecated
    public Map<String, Object> buildAsMap() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(schema));
    }

    public static Map<String, Object> simpleProperty(GeminiSchemaType type, String description) {
        Map<String, Object> prop = new LinkedHashMap<>();
        prop.put("type", type.name());
        prop.put("description", description);
        return Collections.unmodifiableMap(prop);
    }
}
