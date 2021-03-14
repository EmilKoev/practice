package gasStation;

import java.time.LocalDateTime;

public class Loading {

    private Kolonka kolonka;
    private int kolonkaId;
    private String fuelType;
    private int liters;
    private LocalDateTime localDateTime;

    public Loading(Kolonka kolonka, String fuelType, int liters, LocalDateTime localDateTime) {
        this.kolonka = kolonka;
        this.fuelType = fuelType;
        this.liters = liters;
        this.localDateTime = localDateTime;
    }
    public Loading(int kolonka, String fuelType, int liters, LocalDateTime localDateTime) {
        this.kolonkaId = kolonka;
        this.fuelType = fuelType;
        this.liters = liters;
        this.localDateTime = localDateTime;
    }


    public int getKolonkaId() {
        return kolonkaId;
    }

    public Kolonka getKolonka() {
        return kolonka;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getLiters() {
        return liters;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return getFuelType() + ", " + getLiters() + " litra, " + getLocalDateTime();
    }
}
