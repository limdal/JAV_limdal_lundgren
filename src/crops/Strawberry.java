package crops;

/**
 * An expensive crop that loses value quickly once ripe, but grows back
 * after each harvest so it only has to be bought once.
 */
public class Strawberry extends Crop {

    /** Money lost for each turn a ripe strawberry stays in the field. */
    private static final int VALUE_LOSS_PER_TURN = 10;

    /** Age of the plant right after a harvest: it grows back faster. */
    private static final int AGE_AFTER_REGROWING = 1;

    public Strawberry() {
        super("Strawberry", 30, 40, 5, 7);
    }

    @Override
    public int getSellPrice() {
        return getBasePrice() - VALUE_LOSS_PER_TURN * getTurnsSinceMature();
    }

    @Override
    public String getDescription() {
        return "loses $" + VALUE_LOSS_PER_TURN
            + " per turn once ready, but grows back after harvest";
    }

    /**
     * The strawberry plant stays in the field and produces new fruit,
     * starting from an older age than a fresh plant.
     */
    @Override
    public Crop regrow() {
        Strawberry newFruit = new Strawberry();
        newFruit.setAge(AGE_AFTER_REGROWING);
        return newFruit;
    }
}
