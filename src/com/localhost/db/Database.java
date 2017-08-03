package com.localhost.db;

import javax.xml.transform.Result;
import java.sql.*;

public class Database {
    Connection conn;
    public Database(String url, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql) {
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getResultSetLength(ResultSet resultSet) {
        int size = -1;

        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        } catch(SQLException e) {
            return size;
        }

        return size;
    }

    public void close() {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
