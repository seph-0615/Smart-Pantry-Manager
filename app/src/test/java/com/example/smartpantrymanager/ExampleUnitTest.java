
package com.example.smartpantrymanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void validIngredientName_returnsNull() {
        assertNull(IngredientValidator.validateName("Tomatoes"));
    }

    @Test
    public void emptyIngredientName_returnsError() {
        assertNotNull(IngredientValidator.validateName(""));
    }

    @Test
    public void blankIngredientName_returnsError() {
        assertNotNull(IngredientValidator.validateName("   "));
    }

    @Test
    public void validQuantity_returnsNull() {
        assertNull(IngredientValidator.validateQuantity("2"));
        assertNull(IngredientValidator.validateQuantity("1.5"));
    }

    @Test
    public void emptyQuantity_returnsError() {
        assertNotNull(IngredientValidator.validateQuantity(""));
    }

    @Test
    public void invalidQuantity_returnsError() {
        assertNotNull(IngredientValidator.validateQuantity("abc"));
    }

    @Test
    public void zeroQuantity_returnsError() {
        assertNotNull(IngredientValidator.validateQuantity("0"));
    }

    @Test
    public void negativeQuantity_returnsError() {
        assertNotNull(IngredientValidator.validateQuantity("-2"));
    }

    @Test
    public void nonFiniteQuantity_returnsError() {
        assertNotNull(IngredientValidator.validateQuantity("NaN"));
        assertNotNull(IngredientValidator.validateQuantity("Infinity"));
    }

    @Test
    public void validUnit_returnsNull() {
        assertNull(IngredientValidator.validateUnit("kg"));
    }

    @Test
    public void emptyUnit_returnsError() {
        assertNotNull(IngredientValidator.validateUnit(""));
    }
}
