package parser.CPN;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.ClassType;

import java.util.List;

public class CPNParser {
    private Document document;

    private final static String newPageID = "ID6";
    private final static String placeTag = "place";
    private final static String pageEndingTag = "constraints";

    public CPNParser(Document document) {
        this.document = document;
    }

    public void addDataToDocument(List<ClassType> classTypeList){
        Element page = document.getElementById(newPageID);
        System.out.println("ADDING DATA");
        for (ClassType classType: classTypeList){
            System.out.println("WRITING to XML: " + classType);
            Element place = document.createElement(placeTag);
            page.appendChild(place);
        }
    }

    public Document getParsedDocument(){
        return document;
    }
}
