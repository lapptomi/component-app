package fi.project.domain.services;

import fi.project.dao.ComponentDao;
import fi.project.domain.Component;
import fi.project.domain.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods to add, remove, edit, get and validate components.
 */
public class ComponentService implements ComponentDao {

    private Database database = new Database();

    /**
     * Adds new component to database.
     *
     * @param component Component to add into database
     */
    @Override
    public void create(Component component) {
        if (!componentIsValid(component)) {
            System.out.println("Error adding component");
            return;
        }
        try {
            Statement s = database.getConnection().createStatement();
            String query = "INSERT INTO Components (type, model, manufacturer, serialnumber)" +
                    " VALUES ('%s', '%s', '%s', '%s')";
            s.execute(String.format(query,
                    component.getType(),
                    component.getModel(),
                    component.getManufacturer(),
                    component.getSerialNumber())
            );
            System.out.println("Component added to database!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding component");
        }
    }

    /**
     * Deletes component from database if component with serial number
     * given exists.
     *
     * @param serialnumber Serial number given by user
     */
    @Override
    public void delete(String serialnumber) throws SQLException, ClassNotFoundException {
        if (getComponent(serialnumber) == null) {
            System.out.println("Could not find component with serialnumber " + serialnumber);
            return;
        }
        try {
            Statement s = database.getConnection().createStatement();
            String query = "DELETE FROM Components WHERE serialnumber = '%s'";
            s.execute(String.format(query, serialnumber));
        } catch (Exception e) {
            System.out.println("Error deleting component");
        }
    }

    /**
     * Makes sure that component does not have any empty or null values
     * and serial number does not contain spaces.
     *
     * @param component Component to validate
     */
    public boolean componentIsValid(Component component) {
        if (component.getType() == null) {
            return false;
        } else if (component.getModel() == null || component.getModel().length() < 1) {
            return false;
        } else if (component.getManufacturer() == null || component.getManufacturer().length() < 1) {
            return false;
        } else if (component.getSerialNumber() == null
                || component.getSerialNumber().length() < 1
                || component.getSerialNumber().contains(" ")) {
            return false;
        }
        return true;
    }

    /**
     * Finds component from database by serial number given as parameter.
     *
     * @param serialnumber Input given by user
     *
     * @return Component that was found,
     * or null if component with given serial number does not exist
     */
    @Override
    public Component getComponent(String serialnumber) throws SQLException, ClassNotFoundException {
        Component component = null;
        for (Component c : getAll()) {
            if (c.getSerialNumber().equals(serialnumber)) {
                component = c;
                break;
            }
        }
        return component;
    }

    @Override
    public Component update(int id) {
        return null;
    }

    /**
     * Gets all components from database.
     *
     * @return List of components found from database
     */
    @Override
    public List<Component> getAll() throws ClassNotFoundException, SQLException {
        Statement statement = database.getConnection().createStatement();
        List<Component> components = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Components");
            while (result.next()) {
                String type = result.getString("type");
                String model = result.getString("model");
                String manufacturer = result.getString("manufacturer");
                String serialnumber = result.getString("serialnumber");
                components.add(new Component(type, model, manufacturer, serialnumber));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return components;
    }
}
