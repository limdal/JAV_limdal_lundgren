package player;

import crops.Crop;
import farm.Barn;
import farm.CannotHarvestException;
import farm.Field;
import farm.FieldOccupiedException;
import market.Sellable;

/**
 * The farmer: owns the money, the fields and the barn.
 */
public class Player {

    /** Number of fields every player owns. */
    public static final int FIELD_COUNT = 9;

    private int money;
    private Field[] fields;
    private Barn barn;

    /**
     * Creates a player with empty fields and an empty barn.
     *
     * @param startingMoney the money the player begins the game with
     */
    public Player(int startingMoney) {
        money = startingMoney;
        fields = new Field[FIELD_COUNT];
        barn = new Barn();

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field();
        }
    }

    /**
     * Buys a crop and plants it. The money is only taken if planting works.
     *
     * @param fieldIndex the index of the field, starting at 0
     * @param crop the crop to plant
     * @throws NotEnoughMoneyException if the player cannot pay for the seeds
     * @throws FieldOccupiedException if the field already holds a crop
     */
    public void plantCrop(int fieldIndex, Crop crop)
            throws NotEnoughMoneyException, FieldOccupiedException {
        if (money < crop.getPlantingCost()) {
            throw new NotEnoughMoneyException(crop.getPlantingCost(), money);
        }

        fields[fieldIndex].plant(crop);
        money -= crop.getPlantingCost();
    }

    /**
     * Harvests a field and stores the result in the barn.
     *
     * @param fieldIndex the index of the field, starting at 0
     * @return the item that was put in the barn
     * @throws CannotHarvestException if the field has nothing to harvest
     */
    public Sellable harvestCrop(int fieldIndex) throws CannotHarvestException {
        Sellable harvested = fields[fieldIndex].harvest();
        barn.store(harvested);
        return harvested;
    }

    /**
     * Sells everything stored in the barn.
     *
     * @return the money earned
     */
    public int sellBarnContents() {
        int earned = barn.sellAll();
        money += earned;
        return earned;
    }

    public boolean isBarnEmpty() {
        return barn.isEmpty();
    }

    /**
     * @return the money the player would get by selling the barn now
     */
    public int getBarnValue() {
        return barn.getTotalValue();
    }

    /**
     * @return a list of what is in the barn, ready to be shown
     */
    public String describeBarn() {
        return barn.toString();
    }

    /** Makes every crop one turn older. Called once per turn used. */
    public void growAllCrops() {
        for (Field field : fields) {
            field.growCrop();
        }
    }

    /**
     * Tells whether at least one field still holds a crop. Every crop can
     * still earn money: a growing or ready crop can be harvested, and a
     * rotten one can be turned into compost.
     *
     * @return true if some field is not empty
     */
    public boolean hasCropInFields() {
        for (Field field : fields) {
            if (!field.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public int getMoney() {
        return money;
    }

    /**
     * @param fieldIndex the index of the field, starting at 0
     * @return the field at that index
     */
    public Field getField(int fieldIndex) {
        return fields[fieldIndex];
    }
}
