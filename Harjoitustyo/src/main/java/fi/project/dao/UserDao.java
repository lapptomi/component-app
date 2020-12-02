package fi.project.dao;

import fi.project.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void create(User user);
    User getUser(String username) throws SQLException, ClassNotFoundException;
    User update(String username);
    List<User> getAll() throws ClassNotFoundException, SQLException;
}
