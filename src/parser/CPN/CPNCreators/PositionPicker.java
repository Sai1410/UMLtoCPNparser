package parser.CPN.CPNCreators;

import java.util.ArrayList;
import java.util.List;

public class PositionPicker {
    private final static int DISTANCE_SEPARATOR = 150;

    private final static double STARTING_X = -168.000000;
    private final static double STARTING_Y = 84.000000;

    private static PositionPicker instance;
    private static List<PlacePositions> placePositionsList = new ArrayList<>();

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
        if (placePositionsList.isEmpty()){
            newPlacePositions = new PlacePositions(STARTING_X, STARTING_Y);
        }else {
            newPlacePositions = new PlacePositions(STARTING_X,placePositionsList.size() * DISTANCE_SEPARATOR + STARTING_Y);
        }
        placePositionsList.add(newPlacePositions);
        return newPlacePositions;
    }

    class PlacePositions {

        private final static double TYPE_X_OFFSET = 42.500000;
        private final static double TYPE_Y_OFFSET =  -16.000000;
        private final static double MARK_X_OFFSET = 57.000000;
        private final static double MARK_Y_OFFSET = 23.000000;

        private double placeX;
        private double placeY;
        private double typeX;
        private double typeY;
        private double markX;
        private double markY;

        public PlacePositions(double placeX, double placeY) {
            this.placeX = placeX;
            this.placeY = placeY;
            this.typeX = placeX + TYPE_X_OFFSET;
            this.typeY = placeY + TYPE_Y_OFFSET;
            this.markX = placeX + MARK_X_OFFSET;
            this.markY = placeY + MARK_Y_OFFSET;
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
    }
}
