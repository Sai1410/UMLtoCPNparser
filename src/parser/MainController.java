package parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainController {
    private Stage stage;
    private String umlPath = "";
    private String outputPath = "";

    @FXML private Label resultLabel;
    @FXML private Text umlPathText;
    @FXML private Text outputPathText;

    private List<ClassType> classList = new ArrayList<>();

    @FXML protected void handleUMLChoose(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open UML file");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("UML files (*.uml)", "*.uml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            umlPath = file.getAbsolutePath();
            umlPathText.setText(umlPath);
        }
    }

    @FXML protected void handleOutputChoose(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Output directory");
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            outputPath = file.getAbsolutePath();
            outputPathText.setText(outputPath);
        }
    }

    @FXML protected void handleConvert(ActionEvent event){
        if (outputPath.isEmpty() || umlPath.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Paths unspecified");
            alert.setHeaderText("Paths unspecified");
            alert.setContentText("UML filer or output directory not specified");
            alert.showAndWait();
        } else {
            loadUML();
        }
    }

    private void loadUML(){
        ResourceSet set = new ResourceSetImpl();
        set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        set.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
                .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        Resource res = set.getResource(URI.createFileURI(umlPath), true);

        for (EObject eObject : res.getContents()) {
            ClassType classType = new ClassType(eObject.eClass().getName());
            for (EAttribute attribute : eObject.eClass().getEAllAttributes()) {
                classType.addProperty(new PropertyType(attribute.getName(),attribute.getEAttributeType().getInstanceTypeName()));
            }
            for (EOperation operation : eObject.eClass().getEAllOperations()) {
                classType.addOperation(new OperationType(operation.getName()));
            }
            classList.add(classType);
        }

        StringBuilder result = new StringBuilder();
        for (ClassType classType : classList) {
            result.append(classType.getName());
            result.append(",\n");
            for (PropertyType propertyType : classType.getPropertyList()) {
                result.append(propertyType.getName());
                result.append(",\n");
                result.append(propertyType.getType());
                result.append(",\n");
            }
            for (OperationType operationType : classType.getOperationList()) {
                result.append(operationType.getName());
                result.append(",\n");
            }
            result.append(",\n");
        }

        resultLabel.setText(result.toString());

    }

    void setStage(Stage stage){
        this.stage = stage;
    }
}
