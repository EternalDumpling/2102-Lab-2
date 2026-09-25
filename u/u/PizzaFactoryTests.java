// Handwritten by Alexander Ryan
package u;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import c.CheesePizza;
import c.GlutenFreePizza;
import c.GreekPizza;
import c.PepperoniPizza;
import c.PizzaFactory;

public class PizzaFactoryTests {

    private PizzaFactory factory;

    @Before
    public void setUp() {
        factory = new PizzaFactory();
    }

    @Test
    public void createsCheesePizzaTest() {
        assertEquals(CheesePizza.class, factory.createPizza("cheese").getClass());
    }

    @Test
    public void createsGreekPizzaTest() {
        assertEquals(GreekPizza.class, factory.createPizza("greek").getClass());
    }

    @Test
    public void createsPepperoniPizzaTest() {
        assertEquals(PepperoniPizza.class, factory.createPizza("pepperoni").getClass());
    }

    @Test
    public void createsGlutenFreePizzaTest() {
        assertEquals(GlutenFreePizza.class, factory.createPizza("gluten-free").getClass());
    }

    @Test
    public void handoutSpellingsAreAcceptedTest() {
        assertEquals(PepperoniPizza.class, factory.createPizza("Peperoni").getClass());
        assertEquals(GlutenFreePizza.class, factory.createPizza("GlutenFree").getClass());
    }

    @Test
    public void ignoresCaseAndSurroundingSpacesTest() {
        assertEquals(CheesePizza.class, factory.createPizza("  Cheese ").getClass());
        assertEquals(GreekPizza.class, factory.createPizza("GREEK").getClass());
    }

    @Test
    public void upperCaseOrderWorksInTurkishLocaleTest() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals(PepperoniPizza.class, factory.createPizza("PEPPERONI").getClass());
        } finally {
            Locale.setDefault(original);
        }
    }

    @Test
    public void everyOrderGetsANewPizzaTest() {
        assertNotSame(factory.createPizza("cheese"), factory.createPizza("cheese"));
    }

    @Test
    public void unknownTypeThrowsTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> factory.createPizza("pineapple"));
        assertEquals("Unknown pizza type: pineapple", e.getMessage());
    }

    @Test
    public void nullTypeThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> factory.createPizza(null));
    }

    @Test
    public void emptyTypeThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> factory.createPizza(""));
    }

    @Test
    public void blankTypeThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> factory.createPizza("   "));
    }
}
