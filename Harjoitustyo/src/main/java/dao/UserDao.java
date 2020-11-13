package dao;

import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void create(User user);
    User getUser(int id) throws SQLException;
    User update(int id);
    List<User> getAll();
}
