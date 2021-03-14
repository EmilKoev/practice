package gasStation;

import util.DBConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Logger implements Runnable {

    TreeMap<Integer, TreeSet<Loading>> stats;
    private int id = 1;

    public Logger() {
        stats = new TreeMap<>((o1,o2) -> o1.compareTo(o2));
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10000);
                getStats();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getStats() {
        String directory = "stats" + id++;
        File file = new File(directory);
        file.mkdir();
        getAllStats(directory);
        getKolonkaStatsFromDb(directory);
        getFuelStats(directory);
        getMoneyStats(directory);
    }

    private void getMoneyStats(String directory) {
        Connection connection = DBConnector.getInstance().getConnection();
        String sql = "SELECT fuel_type,SUM(fuel_quantity) AS fuel " +
                "FROM station_loadings " +
                "WHERE loading_time >= DATE_SUB(NOW(),INTERVAL 5 MINUTE) " +
                "GROUP BY fuel_type";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PrintStream printStream = new PrintStream(directory + "\\moneyStats.txt")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                double price = 0;
                switch (resultSet.getString("fuel_type")){
                    case "DIESEL": price = 2.4;
                    break;
                    case "PETROL": price = 2.0;
                    break;
                    default: price = 1.6;
                    break;
                }
                printStream.println(resultSet.getString("fuel_type") + ": " + resultSet.getInt("fuel") * price + " leva");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getFuelStats(String directory) {
        Connection connection = DBConnector.getInstance().getConnection();
        String sql = "SELECT fuel_type,SUM(fuel_quantity) AS fuel " +
                "FROM station_loadings " +
                "WHERE loading_time >= DATE_SUB(NOW(),INTERVAL 5 MINUTE) " +
                "GROUP BY fuel_type";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PrintStream printStream = new PrintStream(directory + "\\FuelStats.txt")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                printStream.println(resultSet.getString("fuel_type") + " :"+ resultSet.getInt("fuel") + " liters");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void getKolonkaStatsFromDb(String directory) {
        Connection connection = DBConnector.getInstance().getConnection();
        String sql = "SELECT kolonka_id, COUNT(*) AS autos " +
                "FROM station_loadings " +
                "WHERE loading_time >= DATE_SUB(NOW(),INTERVAL 5 MINUTE) " +
                "GROUP BY kolonka_id";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
        PrintStream printStream = new PrintStream(directory + "\\kolonkaStats.txt")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                printStream.println("kolonka " + resultSet.getString("kolonka_id") + ": " + resultSet.getInt("autos") + " autos");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    private void getAllStats(String directory) {
        String sql = "Select id,kolonka_id,fuel_type,fuel_quantity,loading_time " +
                "FROM station_loadings " +
                "WHERE loading_time >= DATE_SUB(NOW(),INTERVAL 5 MINUTE) ";
        Connection connection = DBConnector.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
        PrintStream printStream = new PrintStream(directory + "\\allstats.txt")){
            ResultSet set = ps.executeQuery();
            while (set.next()){
                int kolonkaId = set.getInt("kolonka_id");
                String fuelType = set.getString("fuel_type");
                int liters = set.getInt("fuel_quantity");
                LocalDateTime localDateTime = set.getTimestamp("loading_time").toLocalDateTime();
                if (!stats.containsKey(kolonkaId)){
                    stats.put(kolonkaId,new TreeSet<>((o1,o2) -> o1.getLocalDateTime().compareTo(o2.getLocalDateTime())));
                }
                stats.get(kolonkaId).add(new Loading(kolonkaId, fuelType,liters,localDateTime));
            }
            for (Map.Entry<Integer, TreeSet<Loading>> e : stats.entrySet()) {
                printStream.println(" Kolonka " + e.getKey() + ": ");
                for (Loading l : e.getValue()) {
                    printStream.print("--------");
                    printStream.println(l);
                }
            }

        } catch (SQLException | FileNotFoundException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
}
