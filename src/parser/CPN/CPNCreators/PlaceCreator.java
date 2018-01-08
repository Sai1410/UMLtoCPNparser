package parser.CPN.CPNCreators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.ClassType;

public class PlaceCreator {

    private final static String placeTag = "place";
    private Document document;
    private Element place;

    private PositionPicker.PlacePositions placePositions;

    public static Element placeFromClass(ClassType classType, Document document) {
        PlaceCreator placeCreator = new PlaceCreator(classType, document);
        return placeCreator.getParsedPlace();
    }

    private PlaceCreator(ClassType classType, Document document){
        this.document = document;
        placePositions = PositionPicker.getInstance().getNewPlacePositions();
        place  = createPlace(classType);
    }

    private Element getParsedPlace(){
        return place;
    }

    private Element createPlace(ClassType classType){
        Element place = document.createElement(placeTag);
        place.setAttribute("id",IdCreator.getInstance().getNewId());

        Element posattr = document.createElement("posattr");
        posattr.setAttribute("x",placePositions.getPlaceX());
        posattr.setAttribute("y", placePositions.getPlaceY());
        place.appendChild(posattr);

        Element fillattr = document.createElement("fillattr");
        fillattr.setAttribute("colour", "White");
        fillattr.setAttribute("pattern", "");
        fillattr.setAttribute("filled", "false");
        place.appendChild(fillattr);

        Element lineattr = document.createElement("lineattr");
        lineattr.setAttribute("colour", "Black");
        lineattr.setAttribute("thick", "1");
        lineattr.setAttribute("type", "Solid");
        place.appendChild(lineattr);

        Element textattr = document.createElement("textattr");
        textattr.setAttribute("colour", "Black");
        textattr.setAttribute("bold", "false");
        place.appendChild(textattr);

        Element text = document.createElement("text");
        text.appendChild(document.createTextNode(classType.getName()));
        place.appendChild(text);

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

        Element snap = document.createElement("snap");
        snap.setAttribute("snap_id", "0");
        snap.setAttribute("anchor.horizontal", "0");
        snap.setAttribute("anchor.vertical", "0");
        marking.appendChild(snap);

        place.appendChild(marking);

        place.appendChild(createPlaceType(classType));

        place.appendChild(createInitMarking());

        return place;
    }

    private Element createPlaceType(ClassType classType){
        Element type = document.createElement("type");
        type.setAttribute("id","ID1412323326");

        Element posattr = document.createElement("posattr");
        posattr.setAttribute("x",placePositions.getTypeX());
        posattr.setAttribute("y", placePositions.getTypeY());
        type.appendChild(posattr);

        Element fillattr = document.createElement("lineattr");
        fillattr.setAttribute("colour", "White");
        fillattr.setAttribute("pattern", "Solid");
        fillattr.setAttribute("filled", "false");
        type.appendChild(fillattr);

        Element lineattr = document.createElement("lineattr");
        lineattr.setAttribute("colour", "Black");
        lineattr.setAttribute("thick", "0");
        lineattr.setAttribute("type", "Solid");
        type.appendChild(lineattr);

        Element textattr = document.createElement("textattr");
        textattr.setAttribute("colour", "Black");
        textattr.setAttribute("bold", "false");
        type.appendChild(textattr);

        Element text = document.createElement("text");
        text.appendChild(document.createTextNode(classType.getName()));
        type.appendChild(text);

        return type;
    }

    private Element createInitMarking(){
        Element initMaring = document.createElement("type");
        initMaring.setAttribute("id","ID1412323327");

        Element posattr = document.createElement("posattr");
        posattr.setAttribute("x",placePositions.getMarkX());
        posattr.setAttribute("y", placePositions.getMarkY());
        initMaring.appendChild(posattr);

        Element fillattr = document.createElement("lineattr");
        fillattr.setAttribute("colour", "White");
        fillattr.setAttribute("pattern", "Solid");
        fillattr.setAttribute("filled", "false");
        initMaring.appendChild(fillattr);

        Element lineattr = document.createElement("lineattr");
        lineattr.setAttribute("colour", "Black");
        lineattr.setAttribute("thick", "0");
        lineattr.setAttribute("type", "Solid");
        initMaring.appendChild(lineattr);

        Element textattr = document.createElement("textattr");
        textattr.setAttribute("colour", "Black");
        textattr.setAttribute("bold", "false");
        initMaring.appendChild(textattr);

        Element text = document.createElement("text");
        text.appendChild(document.createTextNode(""));
        initMaring.appendChild(text);

        return initMaring;
    }
}
