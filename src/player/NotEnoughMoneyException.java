package player;

/**
 * Thrown when the player cannot afford something.
 */
public class NotEnoughMoneyException extends Exception {

    /**
     * @param cost the price of what the player wanted to buy
     * @param money the money the player has
     */
    public NotEnoughMoneyException(int cost, int money) {
        super("That costs $" + cost + " but you only have $" + money + ".");
    }
}
