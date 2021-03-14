package gasStation;

import util.DBConnector;

import java.sql.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class CashRegister {

    private Queue<Loading> loadingsWaitForPay;

    public CashRegister() {
        this.loadingsWaitForPay = new LinkedBlockingDeque<>();
    }

    protected synchronized void unload() throws Exception {
        while (isEmpty()){
            wait();
        }
        Thread.sleep(2000);
        Loading loading = loadingsWaitForPay.poll();
        loading.getKolonka().isFree = true;
        insertToDb(loading);
    }

    private void insertToDb(Loading loading) {
        Connection connection = DBConnector.getInstance().getConnection();
        String sql = "INSERT INTO station_loadings (kolonka_id,fuel_type,fuel_quantity,loading_time) VALUES (?,?,?,?);";
        System.out.println(sql);
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,loading.getKolonka().getId());
            ps.setString(2,loading.getFuelType().toString());
            ps.setInt(3,loading.getLiters());
            ps.setTimestamp(4,Timestamp.valueOf(loading.getLocalDateTime()));
            int n = ps.executeUpdate();
            if (n>0){
                System.out.println("insert success!");
            }else {
                System.out.println("insert IS NOT success!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public synchronized void add(Loading loading) {
        loadingsWaitForPay.offer(loading);
        notifyAll();
    }

    public boolean isEmpty() {
        return loadingsWaitForPay.isEmpty();
    }
}
