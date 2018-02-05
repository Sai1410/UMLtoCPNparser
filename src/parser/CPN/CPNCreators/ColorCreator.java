package parser.CPN.CPNCreators;

import com.sun.istack.internal.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.ClassType;
import parser.Entities.PropertyType;
import parser.Entities.SubvertexType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ColorCreator {
    private final static String colorTag = "color";
    private final static String COLSET = "colset";
    private final static String PRODUCT = "product";

    private Element color;
    private List<Element> prerequisitesColors = new ArrayList<>();

    public static List<Element> colorPrerequisites(ClassType classType, Document document){
        ColorCreator colorCreator = new ColorCreator(classType, document, true);
        return  colorCreator.getPrerequisitesColors();
    }

    public static List<Element> colorPrerequisites(SubvertexType subvertexType, Document document){
        ColorCreator colorCreator = new ColorCreator(subvertexType, document, true);
        return  colorCreator.getPrerequisitesColors();
    }
    
    private List<Element> getPrerequisitesColors(){
        return prerequisitesColors;
    }

    public static Element colorFromClass(ClassType classType, Document document) {
        ColorCreator colorCreator = new ColorCreator(classType, document, false);
        return colorCreator.getParsedColor();
    }


    private ColorCreator(ClassType classType, Document document, boolean prerequisites){
        if(prerequisites){
            createPrerequisitesColors(classType, document);
        }else {
            createClassColor(classType, document);
        }
    }

    private ColorCreator(SubvertexType subvertexType, Document document, boolean prerequisites){
        if(prerequisites){
            createPrerequisitesColors(subvertexType, document);
        }
    }
    
    private void createPrerequisitesColors(ClassType classType, Document document){
        for (PropertyType propertyType: classType.getPropertyList()) {
            prerequisitesColors.add(createSinglePrerequisite(propertyType, document));
        }
    }

    private void createPrerequisitesColors(SubvertexType subvertexType, Document document){
    	
    	
    }
    
    private Element createSinglePrerequisite(PropertyType propertyType, Document document){
        return createColor(document,
                IdCreator.getInstance().getNewId(),
                null,
                propertyType.getName().toUpperCase(),
                TypeAssigner.assignType(propertyType),
                createLayout(propertyType));
    }



    private void createClassColor(ClassType classType, Document document){
        color = createColor(document,
                IdCreator.getInstance().getNewId(),
                classType.getPropertyList(),
                classType.getName().toUpperCase(),
                classType.getName().toLowerCase(),
                createLayout(classType)
                );
    }

    private Element createColor(Document document, String tagId, @Nullable List<PropertyType> propertyTypeList, String innerId, String type, String layoutString){
        Element newColor = document.createElement(colorTag);
        newColor.setAttribute("id",tagId);

        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(innerId));
        newColor.appendChild(id);

        if (propertyTypeList != null){
            newColor.appendChild(createProduct(document, propertyTypeList));
        }

        Element className = document.createElement(type);
        newColor.appendChild(className);

        Element layout = document.createElement("layout");
        layout.appendChild(document.createTextNode(layoutString));
        newColor.appendChild(layout);

        return newColor;
    }

    private Element createProduct(Document document, List<PropertyType> propertyTypeList){
        Element product = document.createElement(PRODUCT);

        for (PropertyType propertyType : propertyTypeList) {
            Element property = document.createElement("id");
            property.appendChild(document.createTextNode(propertyType.getName().toUpperCase()));
            product.appendChild(property);
        }
        return product;
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
            stringBuilder.append(propertyType.getName().toUpperCase());
            if (iterator.hasNext()) {
                stringBuilder.append(" ");
                stringBuilder.append("*");
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    private String createLayout(PropertyType propertyType){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(COLSET);
        stringBuilder.append(" ");
        stringBuilder.append(propertyType.getName().toUpperCase());
        stringBuilder.append(" = ");
        stringBuilder.append(TypeAssigner.assignType(propertyType));
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    private Element getParsedColor(){
        return color;
    }
}
