package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }
    private void prepStatementSQL(String sql) {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement pr = connection.prepareStatement(sql)) {
                pr.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createUsersTable()  {
        String sql = "CREATE TABLE IF NOT EXISTS user" +
                " (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), lastName VARCHAR(25), age TINYINT)";
        prepStatementSQL(sql);
    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS user";
        prepStatementSQL(sqlDrop);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSave = "INSERT INTO user (name, lastName, age) Values (?, ?, ?)";
        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement pr = connection.prepareStatement(sqlSave)){
                pr.setString(1, name);
                pr.setString(2, lastName);
                pr.setByte(3, age);
                pr.execute();
                connection.commit();
                System.out.printf("User с именем – %s добавлен в базу данных \n", name);
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM user WHERE Id = ?";
        prepStatementSQL(sqlRemove);
    }

    public List<User> getAllUsers() {
        String addList = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            try (PreparedStatement pr = connection.prepareStatement(addList);
                 ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAge(rs.getByte("age"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE user";
        prepStatementSQL(sqlClean);
    }
}
