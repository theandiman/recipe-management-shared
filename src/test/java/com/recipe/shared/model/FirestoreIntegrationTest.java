package com.recipe.shared.model;

import com.google.cloud.firestore.annotation.PropertyName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that Firestore will correctly map the isPublic field
 * This simulates what Firestore's toObject() method does
 */
class FirestoreIntegrationTest {

    @Test
    void testPropertyNameAnnotationOnGetter() throws Exception {
        // Get the getter method
        Method getter = Recipe.class.getMethod("isPublicRecipe");
        
        // Verify it has the @PropertyName annotation
        PropertyName annotation = getter.getAnnotation(PropertyName.class);
        assertNotNull(annotation, "Getter should have @PropertyName annotation");
        assertEquals("isPublic", annotation.value(), "PropertyName should map to 'isPublic'");
    }
    
    @Test
    void testPropertyNameAnnotationOnSetter() throws Exception {
        // Get the setter method
        Method setter = Recipe.class.getMethod("setPublicRecipe", boolean.class);
        
        // Verify it has the @PropertyName annotation
        PropertyName annotation = setter.getAnnotation(PropertyName.class);
        assertNotNull(annotation, "Setter should have @PropertyName annotation");
        assertEquals("isPublic", annotation.value(), "PropertyName should map to 'isPublic'");
    }
    
    @Test
    void testFirestoreFieldMapping() {
        // This simulates what Firestore does:
        // 1. When writing: It looks for a getter with @PropertyName("isPublic")
        // 2. When reading: It looks for a setter with @PropertyName("isPublic")
        
        Recipe recipe = Recipe.builder()
            .id("test-123")
            .recipeName("Test Recipe")
            .publicRecipe(true)  // This should map to "isPublic" in Firestore
            .build();
        
        // Verify the field is set correctly
        assertTrue(recipe.isPublicRecipe());
        
        // Simulate Firestore write: Find the @PropertyName annotation value
        Method[] methods = Recipe.class.getMethods();
        String firestoreFieldName = null;
        
        for (Method method : methods) {
            if (method.getName().equals("isPublicRecipe") && method.getParameterCount() == 0) {
                PropertyName ann = method.getAnnotation(PropertyName.class);
                if (ann != null) {
                    firestoreFieldName = ann.value();
                    break;
                }
            }
        }
        
        assertEquals("isPublic", firestoreFieldName, 
            "Firestore should use 'isPublic' as the field name based on @PropertyName");
    }
    
    @Test
    void testSetterWithPropertyName() throws Exception {
        Recipe recipe = new Recipe();
        
        // Find the setter with @PropertyName("isPublic")
        Method setter = null;
        for (Method method : Recipe.class.getMethods()) {
            if (method.getName().equals("setPublicRecipe") && 
                method.getParameterCount() == 1 &&
                method.getParameterTypes()[0] == boolean.class) {
                PropertyName ann = method.getAnnotation(PropertyName.class);
                if (ann != null && ann.value().equals("isPublic")) {
                    setter = method;
                    break;
                }
            }
        }
        
        assertNotNull(setter, "Should find setter with @PropertyName('isPublic')");
        
        // Simulate Firestore setting the value
        setter.invoke(recipe, true);
        
        // Verify it was set
        assertTrue(recipe.isPublicRecipe());
    }
}
