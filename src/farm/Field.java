package farm;

import crops.Crop;
import market.Sellable;

/**
 * One plot of land that can hold a single crop at a time.
 */
public class Field {

    private Crop crop;

    public Field() {
        crop = null;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    /**
     * @param newCrop the crop to put in the field
     * @throws FieldOccupiedException if a crop is already growing here
     */
    public void plant(Crop newCrop) throws FieldOccupiedException {
        if (!isEmpty()) {
            throw new FieldOccupiedException(crop.getName());
        }

        crop = newCrop;
    }

    /**
     * Takes what can be sold out of the field. A ready crop is harvested,
     * and if it grows back the new growth stays in the field. A rotten crop
     * is cleared away and turned into compost.
     *
     * @return the harvested crop, or compost if the crop had rotted
     * @throws CannotHarvestException if the field is empty or the crop is
     *         still growing
     */
    public Sellable harvest() throws CannotHarvestException {
        if (isEmpty()) {
            throw new CannotHarvestException(
                "There is nothing to harvest in this field.");
        }

        switch (crop.getState()) {
            case GROWING:
                throw new CannotHarvestException("The " + crop.getName()
                    + " is not ready yet.");
            case ROTTEN:
                crop = null;
                return new Compost();
            default:
                Crop harvestedCrop = crop;
                crop = harvestedCrop.regrow();
                return harvestedCrop;
        }
    }

    /** Makes the crop in this field one turn older, if there is one. */
    public void growCrop() {
        if (!isEmpty()) {
            crop.grow();
        }
    }

    /**
     * @return the crop in this field, or null if the field is empty
     */
    public Crop getCrop() {
        return crop;
    }
}
