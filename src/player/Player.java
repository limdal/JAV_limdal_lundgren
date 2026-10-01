package player;

import farm.Field;
import crops.Crop;

public class Player {

    private int money;
    private Field[] fields;

    public Player(int startingMoney) {

        money = startingMoney;
        fields = new Field[9];

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field();
        }
    }

    public boolean plantCrop(int fieldNumber, Crop crop) {

        Field field = fields[fieldNumber];

        if (!field.isEmpty()) {
            System.out.println("The field is already occupied.");
            return false;
        }

        if (money < crop.getPlantingCost()) {
            System.out.println("You don't have enough money.");
            return false;
        }

        money -= crop.getPlantingCost();
        field.plant(crop);

        return true;
    }

    public boolean harvestCrop(int fieldNumber) {

        Field field = fields[fieldNumber];

        if (field.isEmpty()) {
            System.out.println("There is nothing to harvest.");
            return false;
        }

        Crop crop = field.getCrop();

        if (crop.isRotten()) {
            System.out.println("The " + crop.getName() + " has rotten!");
            field.harvest();
            return true;
        }

        if (!crop.isMature()) {
            System.out.println("The " + crop.getName() + " is not ready.");
            return false;
        }

        Crop harvestedCrop = field.harvest();

        money += harvestedCrop.getSellPrice();

        System.out.println(
            "You harvested " + harvestedCrop.getName()
            + " and earned $" + harvestedCrop.getSellPrice()
        );

        return true;
    }

    public void growAllCrops() {
        for (Field field : fields) {
            field.growCrop();
        }
    }

    public int getMoney() {
        return money;
    }

    public Field[] getFields() {
        return fields;
    }
}