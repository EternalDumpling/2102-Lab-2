// Handwritten by Alexander Ryan
package u;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import c.Pizza;
import c.PizzaFactory;
import c.PizzaState;
import c.PizzaStore;

@RunWith(Parameterized.class)
public class PizzaContractTests {

    @Parameters(name = "{0}")
    public static Collection<Object[]> pizzaTypes() {
        return Arrays.asList(new Object[][] {
                {"cheese"}, {"greek"}, {"pepperoni"}, {"gluten-free"}
        });
    }

    private final String type;
    private Pizza pizza;

    public PizzaContractTests(String type) {
        this.type = type;
    }

    @Before
    public void setUp() {
        pizza = new PizzaFactory().createPizza(type);
    }

    @Test
    public void startsOrderedTest() {
        assertEquals(PizzaState.ORDERED, pizza.getState());
    }

    @Test
    public void hasANameTest() {
        assertNotNull(pizza.getName());
        assertFalse(pizza.getName().isEmpty());
    }

    @Test
    public void eachStepAdvancesTheStateTest() {
        pizza.prepare();
        assertEquals(PizzaState.PREPARED, pizza.getState());
        pizza.bake();
        assertEquals(PizzaState.BAKED, pizza.getState());
        pizza.cut();
        assertEquals(PizzaState.CUT, pizza.getState());
        pizza.box();
        assertEquals(PizzaState.BOXED, pizza.getState());
    }

    @Test
    public void bakingBeforePreparingThrowsTest() {
        assertThrows(IllegalStateException.class, () -> pizza.bake());
        assertEquals(PizzaState.ORDERED, pizza.getState());
    }

    @Test
    public void boxingTwiceThrowsTest() {
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        assertThrows(IllegalStateException.class, () -> pizza.box());
    }

    @Test
    public void storeCanServeThisTypeTest() {
        Pizza served = new PizzaStore(new PizzaFactory()).orderPizza(type);
        assertEquals(PizzaState.BOXED, served.getState());
    }
}
