package gasStation;

public class Cashier extends Employee {

    private CashRegister cashRegister;

    public Cashier(GasStation gasStation) {
        super(gasStation);
        cashRegister = new CashRegister();
    }

    @Override
    public void run() {
        while (true){
            try {
                cashRegister.unload();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void add(Loading loading) {
        cashRegister.add(loading);
    }
}
