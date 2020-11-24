package dao;

import domain.Component;
import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface ComponentDao {
    void create(Component component);
    User getComponent(int id) throws SQLException, ClassNotFoundException;
    User update(int id);
    List<Component> getAll() throws ClassNotFoundException, SQLException;
}
