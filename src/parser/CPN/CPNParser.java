package parser.CPN;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.CPN.CPNCreators.ArcCreator;
import parser.CPN.CPNCreators.ColorCreator;
import parser.CPN.CPNCreators.PlaceCreator;
import parser.CPN.CPNCreators.TransCreator;
import parser.Entities.ClassType;
import parser.Entities.OperationType;

import java.util.List;

public class CPNParser {
    private Document document;

    private final static String newPageID = "ID6";
    private final static String colsetBlockID = "ID1";


    public CPNParser(Document document) {
        this.document = document;
    }

    public void addDataToDocument(List<ClassType> classTypeList){
        Element page = document.getElementById(newPageID);
        Element colsetBlock = document.getElementById(colsetBlockID);
        System.out.println("ADDING DATA");
        for (ClassType classType: classTypeList){

            Element place = PlaceCreator.placeFromClass(classType, document);
            page.appendChild(place);
            for (Element prerequisite :ColorCreator.colorPrerequisites(classType,document)) {
                colsetBlock.appendChild(prerequisite);
            }
            colsetBlock.appendChild(ColorCreator.colorFromClass(classType,document));

            for (OperationType operationType: classType.getOperationList()) {
                Element transition = TransCreator.createTransitionFromOperation(operationType, document);
                page.appendChild(transition);
                Element arc = ArcCreator.createArcForField(place, transition, document);
                page.appendChild(arc);
            }

        }
    }

    public Document getParsedDocument(){
        return document;
    }


}
