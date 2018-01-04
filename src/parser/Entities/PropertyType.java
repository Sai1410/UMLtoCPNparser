package parser.Entities;

public class PropertyType {
    private String name;
    private String type = "";
    private String defaultValue = "";

    public PropertyType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
