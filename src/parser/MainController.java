package parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.CPN.XMLCreator;
import parser.Entities.ClassType;
import parser.UML.UMLReader;
import parser.UML.ValueExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Stage stage;
    private String umlPath = "";
    private String outputPath = "";

    @FXML private Label resultLabel;
    @FXML private Text umlPathText;
    @FXML private Text outputPathText;

    private List<ClassType> classList = new ArrayList<>();
    private XMLCreator creator = new XMLCreator();

    @FXML protected void handleUMLChoose(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open UML file");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("UML files (*.uml)", "*.uml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Open file chooser window
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            umlPath = file.getAbsolutePath();
            umlPathText.setText(umlPath);
            loadUML();
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
            creator.injectUMLData(classList);
            creator.saveXML(outputPath, ValueExtractor.extractFileName(umlPath));
        }
    }

    private void loadUML(){
        UMLReader reader = new UMLReader(umlPath);
        classList = reader.getClassList();
    }

    void setStage(Stage stage){
        this.stage = stage;
    }
}
