package market;

/**
 * Anything the player can store in the barn and sell at the market.
 * Classes implementing this interface do not need to be related:
 * a crop and a bag of compost are both sellable.
 */
public interface Sellable {

    /**
     * @return the name shown to the player
     */
    String getName();

    /**
     * @return the amount of money the player gets when selling this item
     */
    int getSellPrice();
}
