package com.recipe.shared.schema;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

/**
 * Represents a JSON Schema object that can be serialized to JSON for API consumption.
 * This is the return type for schema generation methods in the shared library.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonSchema {

    private final Map<String, Object> schema;

    public JsonSchema(Map<String, Object> schema) {
        this.schema = Collections.unmodifiableMap(new LinkedHashMap<>(schema));
    }

    /**
     * Returns the schema as a map for Jackson serialization.
     */
    @JsonAnyGetter
    public Map<String, Object> getSchema() {
        return schema;
    }

    public Map<String, Object> asMap() {
        return new LinkedHashMap<>(schema);
    }

    @Override
    public String toString() {
        return "JsonSchema" + schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonSchema that = (JsonSchema) o;
        return Objects.equals(schema, that.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schema);
    }
}
