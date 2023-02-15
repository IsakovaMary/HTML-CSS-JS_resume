package com.example.diplom_fx2;

import java.sql.*;
public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "diplom_db";
    private final String LOGIN = "root";
    private final String PASS = "root";


    private Connection dbConn = null;


    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME ;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public  void addLink(String long_link, String short_link){
        String sql = "INSERT INTO `links` (`long_link`, `short_link`) VALUES(?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, long_link);
            prSt.setString(2, short_link);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getShort(){
        String sql = "SELECT `short_link` FROM `links`";
        Statement statement = null;
        try {
            statement = getDbConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExistsLink(String shortLink) {
        String sql = "SELECT `short_link` FROM `links` WHERE `short_link` = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, shortLink);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
