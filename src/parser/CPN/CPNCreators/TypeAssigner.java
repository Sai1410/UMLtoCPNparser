package parser.CPN.CPNCreators;

import parser.Entities.ClassType;
import parser.Entities.PropertyType;

import java.util.Arrays;
import java.util.List;

public class TypeAssigner {
    private static final List<String> basicTypes = Arrays.asList("unit","bool","int","intinf","time","real","string");

    public static String assignType(PropertyType propertyType){
        switch (propertyType.getType()){
            case "Boolean":
                return basicTypes.get(1);
            case "Integer":
                return basicTypes.get(2);
            case "Double":
                return basicTypes.get(5);
            case "Float":
                return basicTypes.get(5);
            case "String":
                return basicTypes.get(6);
            default:
                return basicTypes.get(6);
        }
    }
}
