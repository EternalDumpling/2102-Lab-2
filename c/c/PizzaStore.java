// AI-generated with Claude Code (Claude Opus 5.5); see AI_PROMPT_LOG.md
package c;

public class PizzaStore {

    private final PizzaFactory factory;

    public PizzaStore(PizzaFactory factory) {
        this.factory = factory;
    }

    // Written only against the Pizza interface: whatever the factory hands back
    // goes through the same four steps, with no instanceof checks.
    public Pizza orderPizza(String type) {
        Pizza pizza = factory.createPizza(type);
        if (pizza == null) {
            throw new IllegalArgumentException("Factory returned no pizza for: " + type);
        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    public static void main(String[] args) {
        PizzaStore store = new PizzaStore(new PizzaFactory());

        String[][] orders = {
                {"the pep band", "cheese"},
                {"the student section", "greek"},
                {"a dorm floor", "pepperoni"},
                {"a student with celiac disease", "gluten-free"},
                {"a visiting fan", "pineapple"},
                {"someone who hit Enter too fast", ""}
        };

        int boxed = 0;
        int turnedAway = 0;

        System.out.println("=== Husky Pizza, Storrs CT: UConn game night ===");
        for (int i = 0; i < orders.length; i++) {
            String customer = orders[i][0];
            String type = orders[i][1];
            System.out.println();
            System.out.println("Order " + (i + 1) + " for " + customer + ": \"" + type + "\"");
            try {
                store.orderPizza(type);
                boxed++;
            } catch (IllegalArgumentException e) {
                System.out.println("  Sorry, we can't make that: " + e.getMessage());
                turnedAway++;
            }
        }

        System.out.println();
        System.out.println("=== Game night totals: " + boxed + " pizzas boxed, "
                + turnedAway + " orders turned away ===");
    }
}
