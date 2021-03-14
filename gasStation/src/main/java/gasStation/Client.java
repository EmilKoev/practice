package gasStation;

import util.Util;

public class Client {

    private FuelType fuelType;
    private int liters;

    public Client() {
        fuelType = FuelType.values()[Util.getRandomNumber(0,FuelType.values().length-1)];
        liters = Util.getRandomNumber(10,40);
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public int getLiters() {
        return liters;
    }
}
