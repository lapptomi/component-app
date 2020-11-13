package domain;

import java.sql.*;

public class Database {

    private final Connection connection;

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        // connectionType1 runs database in memory
        // connectionType2 runs database in file "testi.db"
        String connectionType1 =  "jdbc:sqlite::memory:";
        String connectionType2 = "jdbc:sqlite:testi.db";

        this.connection = DriverManager.getConnection(connectionType1);
        initializeDatabase();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void initializeDatabase() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
    }
}
