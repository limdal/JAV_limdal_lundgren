package farm;

/**
 * Thrown when the player tries to plant in a field that already holds a crop.
 */
public class FieldOccupiedException extends Exception {

    /**
     * @param cropName the name of the crop already growing in the field
     */
    public FieldOccupiedException(String cropName) {
        super("This field already holds a " + cropName + ".");
    }
}
