package demo;

import gasStation.GasStation;
import gasStation.Logger;

public class Demo {

    public static void main(String[] args) {

        GasStation gasStation = new GasStation();
        gasStation.startWork();
        Thread t = new Thread(new ClientGenerator(gasStation));
        t.setDaemon(true);
        t.start();
        Thread logger = new Thread(new Logger());
        logger.setDaemon(true);
        logger.start();

    }
}
