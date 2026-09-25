// Handwritten by Alexander Ryan
package u;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import c.GlutenFreePizza;
import c.GreekPizza;
import c.Pizza;
import c.PizzaFactory;
import c.PizzaState;
import c.PizzaStore;

public class PizzaStoreTests {

    private PizzaStore store;

    @Before
    public void setUp() {
        store = new PizzaStore(new PizzaFactory());
    }

    @Test
    public void orderedPizzaComesBackBoxedTest() {
        assertEquals(PizzaState.BOXED, store.orderPizza("cheese").getState());
    }

    @Test
    public void orderedPizzaIsTheRequestedTypeTest() {
        assertEquals(GreekPizza.class, store.orderPizza("greek").getClass());
    }

    @Test
    public void unknownOrderIsRejectedTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> store.orderPizza("pineapple"));
        assertEquals("Unknown pizza type: pineapple", e.getMessage());
    }

    @Test
    public void emptyOrderIsRejectedTest() {
        assertThrows(IllegalArgumentException.class, () -> store.orderPizza(""));
    }

    @Test
    public void storeUsesItsOwnFactoryTest() {
        PizzaStore glutenFreeOnly = new PizzaStore(new PizzaFactory() {
            @Override
            public Pizza createPizza(String type) {
                return new GlutenFreePizza();
            }
        });
        Pizza pizza = glutenFreeOnly.orderPizza("cheese");
        assertEquals(GlutenFreePizza.class, pizza.getClass());
        assertEquals(PizzaState.BOXED, pizza.getState());
    }

    @Test
    public void factoryReturningNullIsRejectedTest() {
        PizzaStore broken = new PizzaStore(new PizzaFactory() {
            @Override
            public Pizza createPizza(String type) {
                return null;
            }
        });
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> broken.orderPizza("cheese"));
        assertEquals("Factory returned no pizza for: cheese", e.getMessage());
    }
}
