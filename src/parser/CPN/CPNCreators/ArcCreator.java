package parser.CPN.CPNCreators;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import parser.Entities.TransitionType;


public class ArcCreator extends CPNCreator {

    public final static String ARC_TAG = "arc";
    public final static String ARROW_ATRR = "arrowattr";

    private Element arc;

    public static Element createArcForField(Element place, Element transition, Document document){
        ArcCreator arcCreator = new ArcCreator(place,transition,document, "BothDirections");
        return arcCreator.getArc();
    }

    public static Element createArcSourceToTransition(List<Element> placeList, String source, Element transition, Document document){
    	ArcCreator arcCreator = null;
    	
    	for(Element place: placeList) {
    		if(place.getAttribute("name").equals(source) || (place.getAttribute("name").equals("output" + source))) {
    			arcCreator = new ArcCreator(place,transition,document, "PlaceToTransition");
    			return arcCreator.getArc();
    		}
    	}
    	return null;
    }
    public static Element createArcTransitionToTarget(List<Element> placeList, String target, Element transition, Document document){
    	ArcCreator arcCreator = null;
    	for(Element place: placeList) {
    		if(place.getAttribute("name").equals(target) || (place.getAttribute("name").equals("input" + target))) {
    			arcCreator = new ArcCreator(place,transition,document, "TransitionToPlace");
    		}
    	}
    	return arcCreator.getArc();
    }  
    public Element getArc() {
        return arc;
    }

    private ArcCreator(Element place, Element transition, Document document, String orientation){
    	switch (orientation) {
    		case "PlaceToTransition":
    			arc = createArc(document,place,transition, "PtoT");
    			break;
    		case "TransitionToPlace":
    			arc = createArc(document,place,transition, "TtoP");
    			break;
    		default:
    			arc = createArc(document,place,transition, "BOTHDIR");
    			break;
    	}
    }
    

    private Element createArc(Document document, Element place, Element transition, String orientation){
        Element newArc = document.createElement(ARC_TAG);
        newArc.setAttribute("id",IdCreator.getInstance().getNewId());
        newArc.setAttribute("orientation", orientation);
        newArc.setAttribute("order","1");

        newArc = addBasicFields(newArc,document,new AttributeType(AttributeType.Types.ARC, (PositionPicker.ArcAnnotationPositions) null),null);

        Element arrowattr = document.createElement(ARROW_ATRR);
        arrowattr.setAttribute("headsize","1.20000");
        arrowattr.setAttribute("currentcyckle","2");
        newArc.appendChild(arrowattr);

        Element transend = document.createElement("transend");
        transend.setAttribute("idref",transition.getAttribute("id"));
        newArc.appendChild(transend);

        Element placeend = document.createElement("placeend");
        placeend.setAttribute("idref",place.getAttribute("id"));
        newArc.appendChild(placeend);

        Element annot = document.createElement("annot");
        annot.setAttribute("id",IdCreator.getInstance().getNewId());

        annot = addBasicFields(annot, document, new AttributeType(AttributeType.Types.ARC_ANNOT,
                PositionPicker.getInstance().getNewArcAnnotPositions(place, transition)), null);
        newArc.appendChild(annot);

        return newArc;
    }
    
}
