package crops;

/**
 * A cheap and fast crop whose price never changes.
 */
public class Carrot extends Crop {

    public Carrot() {
        super("Carrot", 5, 12, 2, 5);
    }

    @Override
    public int getSellPrice() {
        return getBasePrice();
    }

    @Override
    public String getDescription() {
        return "cheap and fast, the price never changes";
    }
}
