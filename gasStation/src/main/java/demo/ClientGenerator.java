package demo;

import gasStation.Client;
import gasStation.GasStation;

public class ClientGenerator implements  Runnable{

    private GasStation gasStation;

    public ClientGenerator(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    @Override
    public void run() {

        while (true){
            try {
                Thread.sleep(2000);
                Client client = new Client();
                gasStation.comingClient(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
