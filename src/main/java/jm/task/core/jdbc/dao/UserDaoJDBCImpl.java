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
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user" +
                " (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), lastName VARCHAR(25), age TINYINT)";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            pr.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS user";
        try (PreparedStatement pr = connection.prepareStatement(sqlDrop)) {
            connection.setAutoCommit(false);
            pr.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSave = "INSERT INTO user (name, lastName, age) Values (?, ?, ?)";
        try (PreparedStatement pr = connection.prepareStatement(sqlSave)) {
            connection.setAutoCommit(false);
            pr.setString(1, name);
            pr.setString(2, lastName);
            pr.setByte(3, age);
            pr.execute();
            connection.commit();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM user WHERE Id = ?";
        try (PreparedStatement pr = connection.prepareStatement(sqlRemove)) {
            connection.setAutoCommit(false);
            pr.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        String addList = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE user";
        try (PreparedStatement pr = connection.prepareStatement(sqlClean)) {
            connection.setAutoCommit(false);
            pr.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
