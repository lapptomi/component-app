package fi.project.domain;

public class Component {

    private String type;
    private String model;
    private String manufacturer;
    private String serialNumber;

    public Component(String type, String model, String manufacturer, String serialNumber) {
        this.type = type;
        this.model = model;
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
