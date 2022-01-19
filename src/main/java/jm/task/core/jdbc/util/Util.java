package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/db_user";
    private static final String UNAME = "root";
    private static final String PAS = "123456789";
    private static Connection connection;

    public static Connection getConnection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(URL,UNAME,PAS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return connection;
    }
}