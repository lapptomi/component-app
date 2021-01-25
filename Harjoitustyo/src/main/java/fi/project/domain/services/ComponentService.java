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
 * This class has methods to add, remove, edit, get and validate components
 */
public class ComponentService implements ComponentDao {

    private Database database = new Database();

    /**
     * Adds new component to the database.
     * @param component Component to add to the database
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
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding component");
        }
    }

    /**
     * Deletes component from the database if the component with serial number given exists
     * @param serialnumber Serial number given by the user
     * @throws ClassNotFoundException if method getComponent throws exception
     * @throws SQLException if method getComponent throws exception
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
     * Checks that the component is valid
     * @param component Component to validate
     * @return True if component is valid, else returns false
     */
    public boolean componentIsValid(Component component) {
        if (component.getType() == null || component.getType().length() < 1) {
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
     * Finds component from the database by the serial number given as parameter
     * @param serialnumber Input given by the user
     * @return Component that was found
     * or null if component with the serial number given does not exist
     * @throws ClassNotFoundException if method getAll throws exception
     * @throws SQLException if method getAll throws exception
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

    /**
     * Gets all components from the database.
     * @return List of components found from the database
     * @throws ClassNotFoundException if getConnection throws exception
     * @throws SQLException if getConnection throws exception
     */
    @Override
    public List<Component> getAll() throws SQLException, ClassNotFoundException {
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

    /**
     * Saves updated components to the database
     * @param components Updated components to add into database
     */
    public void saveChanges(List<Component> components) {
        try {
            Statement statement = database.getConnection().createStatement();
            statement.execute("DELETE FROM Components");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error saving changes");
        }
        components.forEach(component -> create(component));
    }
}
