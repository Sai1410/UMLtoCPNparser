package parser.CPN.CPNCreators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.ClassType;
import parser.Entities.PropertyType;

import java.util.Iterator;

public class ColorCreator {
    private final static String colorTag = "color";
    private final static String startingColorId = "ID1612323325";

    private final static String COLSET = "colset";
    private final static String PRODUCT = "product";

    private Document document;
    private Element color;

    public static Element placeFromClass(ClassType classType, Document document) {
        ColorCreator colorCreator = new ColorCreator(classType, document);
        return colorCreator.getParsedColor();
    }

    private ColorCreator(ClassType classType, Document document){
        color = document.createElement(colorTag);
        color.setAttribute("id",startingColorId);

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(classType.getName().toUpperCase()));
        color.appendChild(id);

        Element className = document.createElement(classType.getName().toLowerCase());
        color.appendChild(className);

        Element layout = document.createElement("layout");
        layout.appendChild(document.createTextNode(createLayout(classType)));
        color.appendChild(layout);

    }

    private String createLayout(ClassType classType){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(COLSET);
        stringBuilder.append(" ");
        stringBuilder.append(classType.getName().toUpperCase());
        stringBuilder.append(" = ");
        stringBuilder.append(PRODUCT);

        Iterator<PropertyType> iterator = classType.getPropertyList().iterator();
        while (iterator.hasNext()){
            PropertyType propertyType = iterator.next();
            stringBuilder.append(" ");
            stringBuilder.append(propertyType.getName());
            if (iterator.hasNext()) {
                stringBuilder.append(" ");
                stringBuilder.append("*");
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    private Element getParsedColor(){
        return color;
    }
}
