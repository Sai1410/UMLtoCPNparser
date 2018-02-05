package parser.CPN;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import parser.Entities.ClassType;
import parser.Entities.StateMachineType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.List;

public class XMLCreator {

    private static final String emptyTemplateName = "empty1.cpn";
    private static final String dtdFile = "cpn.dtd";
    private CPNParser cpnParser;

    public XMLCreator() {
        // Get location of empty CPN template
        InputStream inputStream = getClass().getResourceAsStream(emptyTemplateName);
        loadCPNTemplate(inputStream);

    }

    private void loadCPNTemplate(InputStream inputStream){
        try {
            // Load Document from ImputStream
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    if (systemId.contains("cpn.dtd")){
                        InputStream dtdStream = XMLCreator.class.getResourceAsStream(dtdFile);
                        return new InputSource(dtdStream);
                    }else {
                        return null;
                    }
                }
            });

            // Supply CPNParserWith Epmpy CPN template
            cpnParser = new CPNParser(docBuilder.parse(inputStream));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public void injectUMLData(List<ClassType> classTypeList, List<StateMachineType> stateMachineList){
        cpnParser.addDataToDocument(classTypeList, stateMachineList);
    }

    public void saveXML(String outputFilePath, String fileName){

        try {
            // Prepare transformers for writing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Supply with Document for writing
            DOMSource source = new DOMSource(cpnParser.getParsedDocument());

            //Create new File and specify it as output stream
            StreamResult result = new StreamResult(new File(outputFilePath + "/" + fileName + ".cpn"));
            
            //Write to file
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
