package parser.CPN.CPNCreators;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parser.Entities.OperationType;

import java.util.List;


public class TransCreator {
    private final static String TRANS_TAG = "trans";
    private final static String COND_TAG = "cond";
    private final static String TIME_TAG = "time";
    private final static String CODE_TAG = "code";
    private final static String PRIORITY_TAG = "priority";

    public static Element createTransitionFromOperation(OperationType operationType, Document document){
        TransCreator transCreator = new TransCreator(operationType, document);
        return transCreator.getTransition();
    }

    private PositionPicker.TransPositions transPositions;
    private Element transition;

    public Element getTransition() {
        return transition;
    }

    private TransCreator(OperationType operationType, Document document){
        this.transPositions = PositionPicker.getInstance().getNewTransPositions();
        this.transition = createTransition(operationType,document);
    }

    private Element createTransition(OperationType operationType, Document document){
        Element trans = document.createElement(TRANS_TAG);
        trans.setAttribute("id", IdCreator.getInstance().getNewId());
        trans.setAttribute("explicit","false");

        trans = addBasicFields(trans,document,new AttributeType(AttributeType.Types.TRANS,transPositions));

        Element cond = document.createElement(COND_TAG);
        cond.setAttribute("id", IdCreator.getInstance().getNewId());
        cond = addBasicFields(cond,document,new AttributeType(AttributeType.Types.COND, transPositions));
        trans.appendChild(cond);

        Element time = document.createElement(TIME_TAG);
        time.setAttribute("id", IdCreator.getInstance().getNewId());
        time = addBasicFields(time,document,new AttributeType(AttributeType.Types.TIME, transPositions));
        trans.appendChild(time);

        Element code = document.createElement(CODE_TAG);
        code.setAttribute("id", IdCreator.getInstance().getNewId());
        code = addBasicFields(code,document,new AttributeType(AttributeType.Types.CODE, transPositions));
        trans.appendChild(code);

        Element priority = document.createElement(PRIORITY_TAG);
        priority.setAttribute("id", IdCreator.getInstance().getNewId());
        priority = addBasicFields(priority,document,new AttributeType(AttributeType.Types.PRIORITY, transPositions));
        trans.appendChild(priority);

        return trans;
    }

    private Element addBasicFields(Element trans, Document document, AttributeType attributeType){
        Element posattr = document.createElement("posattr");
        for (AttributeType.Attribute attribute : attributeType.posAtrributes) {
            posattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(posattr);

        Element fillattr = document.createElement("fillattr");
        for (AttributeType.Attribute attribute : attributeType.fillAtrributes) {
            fillattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(fillattr);

        Element lineattr = document.createElement("lineattr");
        for (AttributeType.Attribute attribute : attributeType.lineAtrributes) {
            lineattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(lineattr);

        Element textattr = document.createElement("textattr");
        for (AttributeType.Attribute attribute : attributeType.textAtrributes) {
            textattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(textattr);

        return trans;
    }

    static class AttributeType{
        private Attribute[] posAtrributes;
        private Attribute[] fillAtrributes;
        private Attribute[] lineAtrributes;
        private Attribute[] textAtrributes;

        public Attribute[] getPosAtrributes() {
            return posAtrributes;
        }

        public Attribute[] getFillAtrributes() {
            return fillAtrributes;
        }

        public Attribute[] getLineAtrributes() {
            return lineAtrributes;
        }

        public Attribute[] getTextAtrributes() {
            return textAtrributes;
        }

        public AttributeType(Types type, PositionPicker.TransPositions positions) {
            switch (type){
                case TRANS:
                    posAtrributes = new Attribute[]
                            {new Attribute("x",positions.getTransX()),
                                    new Attribute("y",positions.getTransY())};
                    fillAtrributes = new Attribute[]
                            {new Attribute("color","White"),
                            new Attribute("pattern",""),
                            new Attribute("filled", "false")};
                    lineAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("thick","1"),
                                    new Attribute("type", "Solid")};
                    textAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("bold","false")};
                    break;
                case CODE:
                    posAtrributes = new Attribute[]
                            {new Attribute("x",positions.getCodeX()),
                                    new Attribute("y",positions.getCodeY())};
                    fillAtrributes = new Attribute[]
                            {new Attribute("color","White"),
                                    new Attribute("pattern","Solid"),
                                    new Attribute("filled", "false")};
                    lineAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("thick","0"),
                                    new Attribute("type", "Solid")};
                    textAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("bold","false")};
                    break;
                case TIME:
                    posAtrributes = new Attribute[]
                            {new Attribute("x",positions.getTimeX()),
                                    new Attribute("y",positions.getTimeY())};
                    fillAtrributes = new Attribute[]
                            {new Attribute("color","White"),
                                    new Attribute("pattern","Solid"),
                                    new Attribute("filled", "false")};
                    lineAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("thick","0"),
                                    new Attribute("type", "Solid")};
                    textAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("bold","false")};
                    break;
                case COND:
                    posAtrributes = new Attribute[]
                            {new Attribute("x",positions.getCondX()),
                                    new Attribute("y",positions.getCondY())};
                    fillAtrributes = new Attribute[]
                            {new Attribute("color","White"),
                                    new Attribute("pattern","Solid"),
                                    new Attribute("filled", "false")};
                    lineAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("thick","0"),
                                    new Attribute("type", "Solid")};
                    textAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("bold","false")};
                case PRIORITY:
                    posAtrributes = new Attribute[]
                            {new Attribute("x",positions.getPriorityX()),
                                    new Attribute("y",positions.getPriorityY())};
                    fillAtrributes = new Attribute[]
                            {new Attribute("color","White"),
                                    new Attribute("pattern","Solid"),
                                    new Attribute("filled", "false")};
                    lineAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("thick","0"),
                                    new Attribute("type", "Solid")};
                    textAtrributes = new Attribute[]
                            {new Attribute("color","Black"),
                                    new Attribute("bold","false")};
                    break;
            }


        }

        class Attribute {
            public String attrName;
            public String value;
            public Attribute(String attrName, String value) {
                this.attrName = attrName;
                this.value = value;
            }
        }

        public enum Types{
            TRANS,
            COND,
            TIME,
            CODE,
            PRIORITY
        }
    }
}
