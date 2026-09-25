// AI-generated with Claude Code (Claude Opus 5.5); see AI_PROMPT_LOG.md
package c;

// Bakes and cuts differently from every other pizza, but still honors the Pizza
// contract, so PizzaStore handles it exactly like the others (Liskov substitution).
public class GlutenFreePizza extends AbstractPizza {

    public GlutenFreePizza() {
        super("Gluten-Free Pizza", "rice-flour gluten-free crust", "crushed tomato sauce",
                "mozzarella", "fresh basil");
    }

    @Override
    protected String bakeInstructions() {
        return "14 minutes at 450 degrees F on a separate gluten-free tray";
    }

    @Override
    protected String cutInstructions() {
        return "6 squares with the dedicated gluten-free cutter";
    }
}
