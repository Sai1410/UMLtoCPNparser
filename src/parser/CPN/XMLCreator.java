package parser.CPN;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLWriter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import parser.Entities.ClassType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class XMLCreator {

    private static final String emptyTemplatePath = "src/parser/resources/CPNNets/empty.cpn";
    private CPNParser cpnParser;

    public XMLCreator() {
        try {
            File file = new File(emptyTemplatePath);
            InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            loadCPNTemplateXML(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadCPNTemplateXML(InputStream inputStream){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            cpnParser = new CPNParser(docBuilder.parse(inputStream));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public void convertUMLtoCPN(List<ClassType> classTypeList){
        cpnParser.addDataToDocument(classTypeList);
    }

    public void saveXML(String outputFilePath, String fileName){

        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(cpnParser.getParsedDocument());
            //StreamResult result = new StreamResult(new File(outputFilePath));
            StreamResult result = new StreamResult(new File(outputFilePath+"/"+ fileName+".cpn"));
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }
}
