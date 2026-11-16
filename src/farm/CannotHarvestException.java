package farm;

/**
 * Thrown when a field has nothing that can be harvested: it is empty or
 * its crop is still growing.
 */
public class CannotHarvestException extends Exception {

    /**
     * @param reason the explanation shown to the player
     */
    public CannotHarvestException(String reason) {
        super(reason);
    }
}
