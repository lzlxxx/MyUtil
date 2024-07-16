package org.jdbc;

import java.sql.*;
import java.util.Iterator;
import java.util.Vector;


public class ConnectionPool {
    private String jdbcDriver = ""; // 数据库驱动
    private String dbUrl = ""; // 数据 URL
    private String dbUsername = ""; // 数据库用户名
    private String dbPassword = ""; // 数据库用户密码
    private int initialConnections = 10; // 连接池的初始大小
    private int incrementalConnections = 5; // 连接池自动增加的大小
    private int maxConnections = 50; // 连接池最大的大小
    private Vector<MyConnection> connections = null; // 存放连接池中数据库连接的向量 , 初始时为 null

    public ConnectionPool(String jdbcDriver, String dbUrl, String dbUsername, String dbPassword) {
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    private synchronized void createPool() throws Exception {
        if (connections != null) {
            return;
        }
        connections = new Vector<MyConnection>();
        createConnections(initialConnections);
        System.out.println("数据库连接池创建完成");
    }

    private void createConnections(int n) throws Exception {
        for (int i = 0; i < n; i++) {
            if (maxConnections > 0 && connections.size() >= maxConnections) {
                break;
            }
            connections.add(new MyConnection(newConnection()));
        }
    }

    private Connection newConnection() throws Exception {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        if (connections.size() == 0) {
            DatabaseMetaData metaData = con.getMetaData();
            metaData.getMaxConnections();
            if (metaData.getMaxConnections() > 0 && metaData.getMaxConnections() > maxConnections) {
                maxConnections = metaData.getMaxConnections();
            }
        }
        return con;
    }

    public synchronized Connection getConnection() throws Exception {
        if (connections == null) {
            createPool();
        }
        Connection conn = getFreeConnection();
        while (conn == null) {
            wait(300);
            conn = getFreeConnection();
        }
        return conn;
    }

    private Connection getFreeConnection() throws Exception {
        Connection conn = findFreeConnection();
        if (conn == null) {
            createConnections(incrementalConnections);
            conn = findFreeConnection();
            if (conn == null) {
                return null;
            }
        }
        return conn;
    }

    private Connection findFreeConnection() throws Exception {
        Connection conn = null;
        MyConnection myConn = null;
        Iterator<MyConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            myConn = iterator.next();
            if (!myConn.isBusy()) {
                conn = myConn.getConnection();
                myConn.setBusy(true);
                if (!testConnection(conn)) {
                    conn = newConnection();
                    myConn.setConnection(conn);
                }
                break;
            }
        }
        return conn;
    }

    private boolean testConnection(Connection conn) {
        if (conn == null) {
            return false;
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void returnConnection(Connection conn) throws Exception {
        if (connections == null) {
            return;
        }
        MyConnection myConn = null;
        Iterator<MyConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            myConn = iterator.next();
            if (myConn.getConnection() == conn) {
                myConn.setBusy(false);
                break;
            }
        }
    }

    private void closeConnectionPool() throws Exception {
        if (connections == null) {
            return;
        }
        Iterator<MyConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            MyConnection myConn = iterator.next();
            if (myConn.isBusy()) {
                wait(2000);
                myConn.setBusy(false);
            }
            myConn.getConnection().close();
            iterator.remove();
        }
        connections = null;
    }
//
//    public static void main(String[] args) {
//        ConnectionPool test = new ConnectionPool("com.mysql.cj.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/xxx", "test", "123456");
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 5000; ++i) {
//            Connection connection = null;
//            try {
//                connection = test.getConnection();
//                test.returnConnection(connection);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("连接MySQL5000次耗时" + (end - start) + "ms");
//    }
}

class MyConnection {
    Connection connection = null;
    boolean busy = false;

    public MyConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
