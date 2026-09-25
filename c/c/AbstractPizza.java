// AI-generated with Claude Code (Claude Opus 5.5); see AI_PROMPT_LOG.md
package c;

public abstract class AbstractPizza implements Pizza {

    private final String name;
    private final String dough;
    private final String sauce;
    private final String[] toppings;
    private PizzaState state = PizzaState.ORDERED;

    protected AbstractPizza(String name, String dough, String sauce, String... toppings) {
        this.name = name;
        this.dough = dough;
        this.sauce = sauce;
        this.toppings = toppings;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PizzaState getState() {
        return state;
    }

    // The four steps are final so no subclass can skip or reorder them.
    // Subclasses change the details through the protected hooks instead.
    @Override
    public final void prepare() {
        advance(PizzaState.ORDERED, PizzaState.PREPARED);
        System.out.println("  Preparing " + name + " (" + dough + ", " + sauce + ", "
                + String.join(", ", toppings) + ")");
    }

    @Override
    public final void bake() {
        advance(PizzaState.PREPARED, PizzaState.BAKED);
        System.out.println("  Baking " + bakeInstructions());
    }

    @Override
    public final void cut() {
        advance(PizzaState.BAKED, PizzaState.CUT);
        System.out.println("  Cutting into " + cutInstructions());
    }

    @Override
    public final void box() {
        advance(PizzaState.CUT, PizzaState.BOXED);
        System.out.println("  Boxing " + name);
    }

    protected String bakeInstructions() {
        return "10 minutes at 650 degrees F in the coal-fired oven";
    }

    protected String cutInstructions() {
        return "8 triangle slices";
    }

    private void advance(PizzaState required, PizzaState next) {
        if (state != required) {
            throw new IllegalStateException(name + " is " + state + ", but must be "
                    + required + " before it can be " + next);
        }
        state = next;
    }
}
