package com.recipe.shared.model;

import com.google.cloud.firestore.annotation.PropertyName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify Firestore serialization behavior with Lombok
 */
class FirestoreSerializationTest {

    @Test
    void testPublicRecipeGetterSetterNames() throws Exception {
        // Verify Lombok generates the correct method names
        Method getter = Recipe.class.getMethod("isPublicRecipe");
        Method setter = Recipe.class.getMethod("setPublicRecipe", boolean.class);
        
        assertNotNull(getter, "Getter isPublicRecipe() should exist");
        assertNotNull(setter, "Setter setPublicRecipe(boolean) should exist");
        
        // Check if @PropertyName annotation is present on the GETTER method (applied via Lombok onMethod)
        PropertyName getterAnnotation = getter.getAnnotation(PropertyName.class);
        PropertyName setterAnnotation = setter.getAnnotation(PropertyName.class);
        
        assertNotNull(getterAnnotation, "@PropertyName should be present on getter method");
        assertEquals("isPublic", getterAnnotation.value(), "@PropertyName value should be 'isPublic' on getter");
        
        assertNotNull(setterAnnotation, "@PropertyName should be present on setter method");
        assertEquals("isPublic", setterAnnotation.value(), "@PropertyName value should be 'isPublic' on setter");
    }
    
    @Test
    void testPublicRecipeFieldAccess() {
        Recipe recipe = Recipe.builder()
            .id("test-123")
            .publicRecipe(true)
            .build();
        
        assertTrue(recipe.isPublicRecipe(), "isPublicRecipe() should return true");
        
        recipe.setPublicRecipe(false);
        assertFalse(recipe.isPublicRecipe(), "isPublicRecipe() should return false after setting to false");
    }
}
