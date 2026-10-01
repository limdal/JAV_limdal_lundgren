package crops;

import interfaces.Sellable;

public abstract class Crop implements Sellable {

    private String name;
    private int age;
    private int plantingCost;
    private int sellPrice;
    private int matureAge;
    private int rottenAge;

    public Crop(String name, int plantingCost, int sellPrice,
                int matureAge, int rottenAge) {

        this.name = name;
        this.plantingCost = plantingCost;
        this.sellPrice = sellPrice;
        this.matureAge = matureAge;
        this.rottenAge = rottenAge;
        this.age = 0;
    }

    public void grow() {
        age++;
    }

    public boolean isMature() {
        return age >= matureAge;
    }

    public boolean isRotten() {
        return age >= rottenAge;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getPlantingCost() {
        return plantingCost;
    }

    @Override
    public int getSellPrice() {
        return sellPrice;
    }

    public int getMatureAge() {
        return matureAge;
    }

    public int getRottenAge() {
        return rottenAge;
    }
}