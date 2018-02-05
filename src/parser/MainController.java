package parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.CPN.XMLCreator;
import parser.Entities.ClassType;
import parser.Entities.StateMachineType;
import parser.UML.UMLReader;
import parser.UML.ValueExtractor;
import parser.Utils.ResultType;

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
    private List<StateMachineType> stateMachineList = new ArrayList<>();
    private XMLCreator creator = new XMLCreator();
    private ResultType resultType;

    public void initialize(){
        changeResultType(new ResultType(ResultType.Type.UML_NOT_PICKED));
    }



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
            checkState();
        }
        /*
        umlPath = "C:/Users/PSidor/Documents/StudioProjektowe_WS/umlparser/state_machine/State_machine.uml";
        umlPathText.setText(umlPath);
        loadUML();
        checkState();*/

    }

    @FXML protected void handleOutputChoose(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Output directory");
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            outputPath = file.getAbsolutePath();
            outputPathText.setText(outputPath);
            checkState();
        }
        /*
        outputPath = "C:/Users/PSidor/Documents/StudioProjektowe_WS/umlparser/state_machine/";
        outputPathText.setText(umlPath);
        checkState();*/
    
    }

    private void checkState() {
        ResultType resultType;
        if (umlPath.isEmpty()) {
            resultType = new ResultType(ResultType.Type.UML_NOT_PICKED);
        } else if (outputPath.isEmpty()) {
            resultType = new ResultType(ResultType.Type.OUTPUT_NOT_PICKED);
        } else {
            resultType = new ResultType(ResultType.Type.READY_TO_PARSE);
        }
        changeResultType(resultType);
    }

    @FXML protected void handleConvert(ActionEvent event){
        if (outputPath.isEmpty() || umlPath.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Paths unspecified");
            alert.setHeaderText("Paths unspecified");
            alert.setContentText("UML filer or output directory not specified");
            alert.showAndWait();
        } else {
        	
            creator.injectUMLData(classList, stateMachineList);
            creator.saveXML(outputPath, ValueExtractor.extractFileName(umlPath));
            changeResultType(new ResultType(ResultType.Type.GENERATED));
        }
    }

    private void loadUML(){
        UMLReader reader = new UMLReader(umlPath);
        classList = reader.getClassList();
        stateMachineList = reader.getStateMachineList();
    }

    private void changeResultType(ResultType type){
        resultType = type;
        setResultLabel();
    }

    private void setResultLabel(){
        resultLabel.setText(resultType.getResult());
        Paint textPaint;
        if (resultType.isTypeGood()){
            textPaint = Color.web("#28d200");
        }else {
            textPaint = Color.web("#ed0a0a");
        }
        resultLabel.setTextFill(textPaint);
    }

    void setStage(Stage stage){
        this.stage = stage;
    }
}
