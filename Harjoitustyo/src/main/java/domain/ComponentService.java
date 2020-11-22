package domain;

import dao.ComponentDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComponentService implements ComponentDao {

    private Database database = new Database();

    @Override
    public void create(Component component) {
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
            System.out.println("Error adding user");
        }
    }

    @Override
    public User getComponent(String serialnumber)  {
        return null;
    }

    @Override
    public User update(String serialnumber) {
        return null;
    }

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
