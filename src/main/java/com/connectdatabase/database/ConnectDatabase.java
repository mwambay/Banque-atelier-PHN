package com.connectdatabase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    String jdbcUrl = "jdbc:mysql://localhost:3306/";
    String username = "root"; 
    String password = ""; 
    String databaseName = "myBanque";

    public ConnectDatabase(String jdbcUrl, String username, String password, String databaseName) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    public boolean connect(){
        try{
           Connection connection = DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
 
            if (connection != null) {
                System.out.println("Connected to the database!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            }
        return false;
    }
}

