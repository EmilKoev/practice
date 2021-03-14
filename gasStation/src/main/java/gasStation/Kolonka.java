package gasStation;

import util.Util;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Kolonka {

    private GasStation gasStation;
    private volatile Queue<Client> clients;
    protected boolean isFree;
    private int id;
    private static int uniqueId = 1;

    protected Kolonka(GasStation gasStation) {
        this.gasStation = gasStation;
        clients = new LinkedList<>();
        isFree = true;
        id = uniqueId++;
    }

    protected void addClient(Client client) {
        this.clients.add(client);
    }

    protected boolean isFree() {
        return isFree;
    }

    protected int numberOfCars(){
        return clients.size();
    }

    public void loadingClient(Employee employee) {
        Client client = clients.poll();
        int random = Util.getRandomNumber(1,gasStation.getNumberOfCashiers())-1;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Cashier cashier = gasStation.getCashier(random);
        cashier.add(new Loading(this,client.getFuelType().toString(),client.getLiters(),LocalDateTime.now()));
        System.out.println("Employee " + employee.getID() + " is ready with the car!");
    }

    public int getId() {
        return id;
    }
}
