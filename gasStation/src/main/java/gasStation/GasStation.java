package gasStation;


import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GasStation {
    private static final int NUMBER_OF_KOLONKI = 5;
    private static final int NUMBER_OF_EMPLOYEES = 2;
    private static final int NUMBER_OF_CASHIERS = 2;

    private List<Kolonka> kolonki;
    private Set<Employee> employees;
    private ArrayList<Cashier> cashiers;

    public GasStation() {
        kolonki = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_KOLONKI; i++) {
            kolonki.add(new Kolonka(this));
        }
        employees = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
            employees.add(new Employee(this));
        }
        cashiers = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CASHIERS; i++) {
            cashiers.add(new Cashier(this));
        }
    }

    public void startWork(){
        for (Employee e : employees) {
            new Thread(e).start();
        }
        for (Cashier c : cashiers) {
            new Thread(c).start();
        }
    }

    public synchronized void comingClient(Client client){
        int r = Util.getRandomNumber(1,kolonki.size()) -1;
        kolonki.get(r).addClient(client);
        notifyAll();
    }


    public synchronized Kolonka getKolkonka() throws InterruptedException {
        while (true) {
            for (Kolonka k : kolonki) {
                if (k.isFree() && k.numberOfCars() > 0) {
                    k.isFree = false;
                    return k;
                }
            }
            wait();
        }
    }

    public int getNumberOfCashiers() {
        return NUMBER_OF_CASHIERS;
    }

    public Cashier getCashier(int random) {
        return cashiers.get(random);
    }
}
