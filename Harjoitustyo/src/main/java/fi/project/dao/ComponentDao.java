package fi.project.dao;

import fi.project.domain.Component;

import java.sql.SQLException;
import java.util.List;

public interface ComponentDao {
    void create(Component component);
    Component getComponent(int id) throws SQLException, ClassNotFoundException;
    Component update(int id);
    List<Component> getAll() throws ClassNotFoundException, SQLException;
}
