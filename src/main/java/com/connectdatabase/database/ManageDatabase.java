package com.connectdatabase.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

class ManageDatabase {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/myBanque";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        ManageDatabase connecterALaBD = new ManageDatabase();
        //connecterALaBD.createTable();
        //connecterALaBD.insertData();
        connecterALaBD.selectData();
    }

    public Connection connect() {
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUE)";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'users' créée avec succès!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData() {
        String insertDataSQL = "INSERT INTO users (name, email) VALUES "
                + "('Jenovic Mwambay', '21MK406@esisalama.org'), "
                + "('Steve Jobs', 'Steve.Jobs@example.com')";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(insertDataSQL);
            System.out.println("Données insérées avec succès!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void selectData() {
        String selectDataSQL = "SELECT * FROM users";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectDataSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
