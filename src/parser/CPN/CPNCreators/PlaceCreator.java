package parser.CPN.CPNCreators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.ClassType;
import parser.Entities.ConnectionPointType;
import parser.Entities.StateMachineType;
import parser.Entities.SubvertexType;

public class PlaceCreator extends CPNCreator {

    private final static String placeTag = "place";
    private final static String initMarkTag = "initmark";
    private final static String typeTag = "type";
    private Element place;
    private PositionPicker.PlacePositions placePositions;

    private PlaceCreator(ClassType classType, Document document){
        placePositions = PositionPicker.getInstance().getNewPlacePositions();
        place  = createPlace(classType, document);
    }
    private PlaceCreator(SubvertexType subvertexType, Document document, String name){
        placePositions = PositionPicker.getInstance().getNewPlacePositions();
        place  = createPlace(subvertexType, document, name);
    }
    
    private PlaceCreator(String name, Document document){
        placePositions = PositionPicker.getInstance().getNewPlacePositions();
        place  = createPlace(name, document);
    }
    
    private PlaceCreator(ConnectionPointType connectionType, Document document){
        placePositions = PositionPicker.getInstance().getNewPlacePositions();
        place  = createPlace(connectionType, document);
    }
    
    public static Element placeFromClass(ClassType classType, Document document) {
        PlaceCreator placeCreator = new PlaceCreator(classType, document);
        return placeCreator.getPlace();
    }

    public static Element placeFromSubvertex(SubvertexType subvertexType, Document document, String name) {
        PlaceCreator placeCreator = new PlaceCreator(subvertexType, document, name);
        
        return placeCreator.getPlace();
    }
    
    public static Element placeFromConnectionPoint(ConnectionPointType connectionType, Document document) {
        PlaceCreator placeCreator = new PlaceCreator(connectionType, document);
        
        return placeCreator.getPlace();
    }
    
    public static Element placeForInputOutput(String name, Document document) {
        PlaceCreator placeCreator = new PlaceCreator(name, document);
        
        return placeCreator.getPlace();
    }
    
    

    private Element getPlace(){
        return place;
    }
    
    private Element createPlace(String name, Document document){
        Element place = document.createElement(placeTag);
        place.setAttribute("id",IdCreator.getInstance().getNewId());
        
        if (name == null) {
        	place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), name);
        } else {
        	place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), name);
        }
        
        this.setBasicAttributes(document, place);
        place.appendChild(createPlaceType(document));
        place.appendChild(createInitMarking(document));
        
        return place;
    }
    
    private Element createPlace(SubvertexType subvertexType, Document document, String name){
        Element place = document.createElement(placeTag);
        place.setAttribute("id",IdCreator.getInstance().getNewId());
        
        if (name == null) {
        	place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), subvertexType.getName());
        } else {
        	place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), name + subvertexType.getName());
        }
        
        this.setBasicAttributes(document, place);
        place.appendChild(createPlaceType(document));
        place.appendChild(createInitMarking(document));
        
        return place;
    }
    
    private Element createPlace(ClassType classType, Document document){
        Element place = document.createElement(placeTag);
        place.setAttribute("id",IdCreator.getInstance().getNewId());

        place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), classType.getName());
        
        this.setBasicAttributes(document, place);
        place.appendChild(createPlaceType(classType, document));
        place.appendChild(createInitMarking(document));
        
        return place;
    }
    
    private Element createPlace(ConnectionPointType connectionPoint, Document document){
        Element place = document.createElement(placeTag);
        place.setAttribute("id",IdCreator.getInstance().getNewId());

        place = addBasicFields(place, document, new AttributeType(AttributeType.Types.PLACE, placePositions), connectionPoint.getName());
        
        this.setBasicAttributes(document, place);
        place.appendChild(createPlaceType(document));
        place.appendChild(createInitMarking(document));
        
        return place;
    }

    private Element createPlaceType(ClassType classType, Document document){
        Element type = document.createElement(typeTag);
        type.setAttribute("id",IdCreator.getInstance().getNewId());
        type = addBasicFields(type, document, new AttributeType(AttributeType.Types.TYPE, placePositions), classType.getName().toUpperCase());

        return type;
    }

    private Element createPlaceType(Document document){
        Element type = document.createElement(typeTag);
        type.setAttribute("id",IdCreator.getInstance().getNewId());
        type = addBasicFields(type, document, new AttributeType(AttributeType.Types.TYPE, placePositions),"");

        return type;
    }
    
    private Element createInitMarking(Document document){
        Element initMaring = document.createElement(initMarkTag);
        initMaring.setAttribute("id",IdCreator.getInstance().getNewId());
        initMaring = addBasicFields(initMaring, document, new AttributeType(AttributeType.Types.INITMARKING, placePositions),null);

        return initMaring;
    }
    
    private void setBasicAttributes(Document document, Element place) {
        Element ellipse = document.createElement("ellipse");
        ellipse.setAttribute("w","60.000000");
        ellipse.setAttribute("h","40.000000");
        place.appendChild(ellipse);
        
        Element token = document.createElement("token");
        token.setAttribute("x", "-10.000000");
        token.setAttribute("y","0.000000");
        place.appendChild(token);
        
        Element marking = document.createElement("marking");
        marking.setAttribute("x", "0.000000");
        marking.setAttribute("y", "0.000000");
        marking.setAttribute("hidden", "false");
        place.appendChild(marking);
        
        Element snap = document.createElement("snap");
        snap.setAttribute("snap_id", "0");
        snap.setAttribute("anchor.horizontal", "0");
        snap.setAttribute("anchor.vertical", "0");
        marking.appendChild(snap);
    }
    
}
