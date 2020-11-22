package dao;

import domain.Component;
import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface ComponentDao {
    void create(Component component);
    User getComponent(String username) throws SQLException, ClassNotFoundException;
    User update(String serialnumber);
    List<Component> getAll() throws ClassNotFoundException, SQLException;
}
