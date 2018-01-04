package parser.CPN;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class XMLCreator {

    public XMLCreator(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            loadCPNTemplateXML(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadCPNTemplateXML(InputStream inputStream){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
