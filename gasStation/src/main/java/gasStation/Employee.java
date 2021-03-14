package gasStation;

public class Employee implements Runnable {

    private GasStation gasStation;
    private Kolonka kolonka;
    private static int uniqueId = 1;
    private int id;

    public Employee(GasStation gasStation) {
        this.gasStation = gasStation;
        id = uniqueId++;
    }

    @Override
    public void run() {
        while (true){
            try {
                kolonka = gasStation.getKolkonka();
                if (kolonka != null){
                    kolonka.loadingClient(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getID() {
        return id;
    }
}
