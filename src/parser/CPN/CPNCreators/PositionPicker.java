package parser.CPN.CPNCreators;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class PositionPicker {
    private final static int DISTANCE_SEPARATOR = 150;

    private final static double STARTING_X = -168.000000;
    private final static double STARTING_Y = 84.000000;

    private static PositionPicker instance;
    private static List<PlacePositions> placePositionsList = new ArrayList<>();
    private static List<TransPositions> transPositionsList = new ArrayList<>();

    private static List<ClassPositions> classPositionsList = new ArrayList<>();

    private PositionPicker() {
    }

    public static PositionPicker getInstance(){
        if (instance != null){
            return instance;
        }else {
            instance = new PositionPicker();
        }
        return instance;
    }

    public PlacePositions getNewPlacePositions(){
        PlacePositions newPlacePositions;
        if (classPositionsList.isEmpty()){
            newPlacePositions = new PlacePositions(STARTING_X, STARTING_Y);
        }else {
            newPlacePositions = new PlacePositions(STARTING_X,classPositionsList.size() * DISTANCE_SEPARATOR + STARTING_Y);
        }
        classPositionsList.add(new ClassPositions(newPlacePositions));
        return newPlacePositions;
    }

    public TransPositions getNewTransPositions(){
        TransPositions transPositions;
        double newTransX;
        if (classPositionsList.get(classPositionsList.size()-1).isTransListEmpty()){
            newTransX = STARTING_X + DISTANCE_SEPARATOR;
        }else {
            newTransX = classPositionsList.get(classPositionsList.size()-1).getLastTransPositions().transX + DISTANCE_SEPARATOR;
        }
        transPositions = new TransPositions(newTransX,(classPositionsList.size() - 1) * DISTANCE_SEPARATOR + STARTING_Y);
        classPositionsList.get(classPositionsList.size()-1).addTransPositions(transPositions);
        return transPositions;
    }

    public ArcAnnotationPositions getNewArcAnnotPositions(Element place, Element trans){
        return new ArcAnnotationPositions(place,trans);
    }

    class PlacePositions {

        private final static double TYPE_X_OFFSET = 42.500000;
        private final static double TYPE_Y_OFFSET =  -16.000000;
        private final static double MARK_X_OFFSET = 57.000000;
        private final static double MARK_Y_OFFSET = 23.000000;
        private final static double INIT_MARK_X_OFFSET = 56.0000;
        private final static double INIT_MARK_Y_OFFSET = 32.0000;

        private double placeX;
        private double placeY;
        private double typeX;
        private double typeY;
        private double markX;
        private double markY;
        private double initMarkX;
        private double initMarkY;


        public PlacePositions(double placeX, double placeY) {
            this.placeX = placeX;
            this.placeY = placeY;
            this.typeX = placeX + TYPE_X_OFFSET;
            this.typeY = placeY + TYPE_Y_OFFSET;
            this.markX = placeX + MARK_X_OFFSET;
            this.markY = placeY + MARK_Y_OFFSET;
            this.initMarkX = placeX + INIT_MARK_X_OFFSET;
            this.initMarkY = placeY + INIT_MARK_Y_OFFSET;
        }

        public String getPlaceX() {
            return String.valueOf(placeX);
        }

        public String getPlaceY() {
            return String.valueOf(placeY);
        }

        public String getTypeX() {
            return String.valueOf(typeX);
        }

        public String getTypeY() {
            return String.valueOf(typeY);
        }

        public String getMarkX() {
            return String.valueOf(markX);
        }

        public String getMarkY() {
            return String.valueOf(markY);
        }

        public String getInitMarkX() {
            return String.valueOf(initMarkX);
        }

        public String getInitMarkY() {
            return String.valueOf(initMarkY);
        }
    }

    class TransPositions{
        private final static double COND_X_OFFSET = -39.00000;
        private final static double COND_Y_OFFSET =  26.000000;
        private final static double TIME_X_OFFSET = 44.50000;
        private final static double TIME_Y_OFFSET =  26.000000;
        private final static double CODE_X_OFFSET = 64.50000;
        private final static double CODE_Y_OFFSET =  -47.000000;
        private final static double PRIORITY_X_OFFSET = -68.00000;
        private final static double PRIORITY_Y_OFFSET =  -26.000000;

        private double transX;
        private double transY;
        private double condX;
        private double condY;
        private double timeX;
        private double timeY;
        private double codeX;
        private double codeY;
        private double priorityX;
        private double priorityY;

        public TransPositions(double transX, double transY) {
            this.transX = transX;
            this.transY = transY;
            this.condX = transX + COND_X_OFFSET;
            this.condY = transY + COND_Y_OFFSET;
            this.timeX = transX + TIME_X_OFFSET;;
            this.timeY = transY + TIME_Y_OFFSET;;
            this.codeX = transX + CODE_X_OFFSET;;
            this.codeY = transY + CODE_Y_OFFSET;;
            this.priorityX = transX + PRIORITY_X_OFFSET;;
            this.priorityY = transY + PRIORITY_Y_OFFSET;;
        }

        public String getTransX() {
            return String.valueOf(transX);
        }

        public String getTransY() {
            return String.valueOf(transY);
        }

        public String getCondX() {
            return String.valueOf(condX);
        }

        public String getCondY() {
            return String.valueOf(condY);
        }

        public String getTimeX() {
            return String.valueOf(timeX);
        }

        public String getTimeY() {
            return String.valueOf(timeY);
        }

        public String getCodeX() {
            return String.valueOf(codeX);
        }

        public String getCodeY() {
            return String.valueOf(codeY);
        }

        public String getPriorityX() {
            return String.valueOf(priorityX);
        }

        public String getPriorityY() {
            return String.valueOf(priorityY);
        }
    }

    public class ClassPositions{
        private PlacePositions placePositions;
        private List<TransPositions> transPositionsList;

        public ClassPositions(PlacePositions placePositions) {
            this.placePositions = placePositions;
            this.transPositionsList = new ArrayList<>();
        }

        public PlacePositions getPlacePositions() {
            return placePositions;
        }

        public boolean isTransListEmpty(){
            return transPositionsList.isEmpty();
        }

        public TransPositions getLastTransPositions() {
            return transPositionsList.get(transPositionsList.size()-1);
        }

        public void addTransPositions(TransPositions transPositions) {
            transPositionsList.add(transPositions);
        }
    }

    public class ArcAnnotationPositions{
        private double x;
        private double y;

        public ArcAnnotationPositions(Element place, Element transition){
           NodeList placePos = place.getElementsByTagName("posattr");
            double placeX = Double.valueOf(placePos.item(0).getAttributes().getNamedItem("x").getNodeValue());
            double placeY = Double.valueOf(placePos.item(0).getAttributes().getNamedItem("y").getNodeValue());

            NodeList transitionPos = transition.getElementsByTagName("posattr");
            double transitionX = Double.valueOf(transitionPos.item(0).getAttributes().getNamedItem("x").getNodeValue());
            double transitionY = Double.valueOf(transitionPos.item(0).getAttributes().getNamedItem("y").getNodeValue());

            this.x = (placeX + transitionX) / 2.0;
            this.y = (placeY + transitionY) / 2.0;
        }

        public String getX() {
            return String.valueOf(x);
        }

        public String getY() {
            return String.valueOf(y);
        }
    }
}
