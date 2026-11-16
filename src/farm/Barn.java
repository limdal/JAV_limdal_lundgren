package farm;

import java.util.ArrayList;

import market.Sellable;

/**
 * Stores everything the player has harvested until it is sold.
 * Items in the barn no longer grow, so their price stays the same.
 */
public class Barn {

    private ArrayList<Sellable> items;

    public Barn() {
        items = new ArrayList<Sellable>();
    }

    public void store(Sellable item) {
        items.add(item);
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    /**
     * @return the money the player would get by selling everything now
     */
    public int getTotalValue() {
        int total = 0;

        for (Sellable item : items) {
            total += item.getSellPrice();
        }

        return total;
    }

    /**
     * Empties the barn.
     *
     * @return the money earned by selling every item
     */
    public int sellAll() {
        int earned = getTotalValue();
        items = new ArrayList<Sellable>();
        return earned;
    }

    /**
     * Lists the stored items, for example "Carrot ($12), Compost ($2)".
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "empty";
        }

        String description = "";

        for (int i = 0; i < items.size(); i++) {
            Sellable item = items.get(i);

            if (i > 0) {
                description += ", ";
            }
            description += item.getName() + " ($" + item.getSellPrice() + ")";
        }

        return description;
    }
}
