package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/db_user";
    private static final String UNAME = "root";
    private static final String PAS = "123456789";

    public static Connection getConnection() throws SQLException{
            return DriverManager.getConnection(URL,UNAME,PAS);
    }
}