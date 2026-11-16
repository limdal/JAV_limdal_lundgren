package crops;

/**
 * A crop that keeps well: it becomes more valuable for every turn it
 * stays in the field after maturing, until it rots.
 */
public class Potato extends Crop {

    /** Money gained for each turn a ripe potato stays in the field. */
    private static final int VALUE_GAIN_PER_TURN = 4;

    public Potato() {
        super("Potato", 10, 25, 4, 7);
    }

    @Override
    public int getSellPrice() {
        return getBasePrice() + VALUE_GAIN_PER_TURN * getTurnsSinceMature();
    }

    @Override
    public String getDescription() {
        return "worth $" + VALUE_GAIN_PER_TURN
            + " more for every turn you wait after it is ready";
    }
}
