package parser.CPN;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.CPN.CPNCreators.ColorCreator;
import parser.CPN.CPNCreators.PlaceCreator;
import parser.Entities.ClassType;

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
            page.appendChild(PlaceCreator.placeFromClass(classType, document));
            colsetBlock.appendChild(ColorCreator.placeFromClass(classType,document));
        }
    }

    public Document getParsedDocument(){
        return document;
    }


}
