package farm;

import market.Sellable;

/**
 * What is left when a rotten crop is cleared from a field. It is not a
 * crop, but it can still be sold for a small amount.
 */
public class Compost implements Sellable {

    private static final int PRICE = 2;

    @Override
    public String getName() {
        return "Compost";
    }

    @Override
    public int getSellPrice() {
        return PRICE;
    }
}
