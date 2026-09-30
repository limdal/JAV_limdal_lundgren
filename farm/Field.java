package farm;

import crops.Crop;

public class Field {

    private Crop crop;

    public Field() {
        crop = null;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    public void plant(Crop crop) {
        if (!isEmpty()) {
            System.out.println("This field is already occupied.");
            return;
        }

        this.crop = crop;
    }

    public Crop harvest() {
        if (isEmpty()) {
            return null;
        }

        Crop harvestedCrop = crop;
        crop = null;

        return harvestedCrop;
    }

    public void growCrop() {
        if (!isEmpty()) {
            crop.grow();
        }
    }

    public Crop getCrop() {
        return crop;
    }
}