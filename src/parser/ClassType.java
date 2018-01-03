package parser;

import java.util.ArrayList;
import java.util.List;

public class ClassType {
    private String name;
    private List<PropertyType> propertyList = new ArrayList<>();
    private List<OperationType> operationList = new ArrayList<>();

    public ClassType(String name) {
        this.name = name;
    }

    public void addProperty(PropertyType propertyType){
        propertyList.add(propertyType);
    }

    public void addOperation(OperationType operationType){
        operationList.add(operationType);
    }

    public String getName() {
        return name;
    }

    public List<PropertyType> getPropertyList() {
        return propertyList;
    }

    public List<OperationType> getOperationList() {
        return operationList;
    }
}
