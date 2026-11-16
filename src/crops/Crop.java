package crops;

import market.Sellable;

/**
 * A plant that grows in a field, one turn at a time. A crop can be
 * harvested once it is mature and rots if it is left in the field too long.
 *
 * Crop does not implement getSellPrice() from Sellable: each kind of crop
 * decides how its price changes after it has matured, so the class stays
 * abstract.
 */
public abstract class Crop implements Sellable {

    private final String name;
    private final int plantingCost;
    private final int basePrice;
    private final int matureAge;
    private final int rottenAge;
    private int age;

    /**
     * @param name the name shown to the player
     * @param plantingCost the price of the seeds
     * @param basePrice the selling price on the turn the crop matures
     * @param matureAge the age, in turns, at which the crop can be harvested
     * @param rottenAge the age, in turns, at which the crop is lost
     */
    public Crop(String name, int plantingCost, int basePrice,
                int matureAge, int rottenAge) {
        this.name = name;
        this.plantingCost = plantingCost;
        this.basePrice = basePrice;
        this.matureAge = matureAge;
        this.rottenAge = rottenAge;
        this.age = 0;
    }

    /**
     * @return a short sentence telling the player how this crop behaves
     */
    public abstract String getDescription();

    /**
     * Gives what is left in the field after this crop is harvested.
     * Most crops leave nothing behind, so this returns null by default;
     * crops that grow back override it.
     *
     * @return the crop that stays in the field, or null if the field is empty
     */
    public Crop regrow() {
        return null;
    }

    /** Makes the crop one turn older. */
    public void grow() {
        age++;
    }

    /**
     * @return the stage of life the crop is in at its current age
     */
    public CropState getState() {
        if (age >= rottenAge) {
            return CropState.ROTTEN;
        } else if (age >= matureAge) {
            return CropState.READY;
        } else {
            return CropState.GROWING;
        }
    }

    /**
     * @return how many turns the crop has been mature, 0 if it is not yet
     */
    protected int getTurnsSinceMature() {
        return Math.max(0, age - matureAge);
    }

    /**
     * Changes the age of the crop. Only used by crops that grow back,
     * which start their new growth at an older age.
     *
     * @param age the new age, in turns
     */
    protected void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getPlantingCost() {
        return plantingCost;
    }

    /**
     * @return the selling price on the turn the crop becomes ready; the
     *         real price at a later turn is given by getSellPrice()
     */
    public int getBasePrice() {
        return basePrice;
    }

    public int getMatureAge() {
        return matureAge;
    }

    public int getRottenAge() {
        return rottenAge;
    }

    /**
     * Describes the crop and its state, for example "Potato [Growing 2/4]".
     */
    @Override
    public String toString() {
        switch (getState()) {
            case GROWING:
                return name + " [Growing " + age + "/" + matureAge + "]";
            case READY:
                return name + " [READY, worth $" + getSellPrice() + "]";
            default:
                return name + " [ROTTEN]";
        }
    }
}
