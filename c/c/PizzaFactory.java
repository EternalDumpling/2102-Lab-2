// Handwritten by Alexander Ryan
package c;

import java.util.Locale;

public class PizzaFactory {

    public Pizza createPizza(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("No pizza type was given");
        }
        return switch (type.trim().toLowerCase(Locale.ROOT)) {
            case "cheese" -> new CheesePizza();
            case "greek" -> new GreekPizza();
            case "pepperoni", "peperoni" -> // the lab handout's spelling
                    new PepperoniPizza();
            case "gluten-free", "glutenfree" -> new GlutenFreePizza();
            default -> throw new IllegalArgumentException("Unknown pizza type: " + type);
        };
    }
}
