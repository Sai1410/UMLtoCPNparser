package parser.CPN.CPNCreators;

import com.sun.istack.internal.Nullable;
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


        trans = addBasicFields(trans,document,new AttributeType(AttributeType.Types.TRANS,transPositions),operationType.getName());

        Element box = document.createElement("box");
        box.setAttribute("w","60.000000");
        box.setAttribute("h","40.000000");
        trans.appendChild(box);

        Element binding = document.createElement("binding");
        binding.setAttribute("x","7.20000");
        binding.setAttribute("y","-3.00000");
        trans.appendChild(binding);

        Element cond = document.createElement(COND_TAG);
        cond.setAttribute("id", IdCreator.getInstance().getNewId());
        cond = addBasicFields(cond,document,new AttributeType(AttributeType.Types.COND, transPositions),null);
        trans.appendChild(cond);

        Element time = document.createElement(TIME_TAG);
        time.setAttribute("id", IdCreator.getInstance().getNewId());
        time = addBasicFields(time,document,new AttributeType(AttributeType.Types.TIME, transPositions),null);
        trans.appendChild(time);

        Element code = document.createElement(CODE_TAG);
        code.setAttribute("id", IdCreator.getInstance().getNewId());
        code = addBasicFields(code,document,new AttributeType(AttributeType.Types.CODE, transPositions),null);
        trans.appendChild(code);

        Element priority = document.createElement(PRIORITY_TAG);
        priority.setAttribute("id", IdCreator.getInstance().getNewId());
        priority = addBasicFields(priority,document,new AttributeType(AttributeType.Types.PRIORITY, transPositions),null);
        trans.appendChild(priority);

        return trans;
    }

    private Element addBasicFields(Element trans, Document document, AttributeType attributeType, @Nullable String textValue){
        Element posattr = document.createElement("posattr");
        for (AttributeType.Attribute attribute : attributeType.getPosAtrributes()) {
            posattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(posattr);

        Element fillattr = document.createElement("fillattr");
        for (AttributeType.Attribute attribute : attributeType.getFillAtrributes()) {
            fillattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(fillattr);

        Element lineattr = document.createElement("lineattr");
        for (AttributeType.Attribute attribute : attributeType.getLineAtrributes()) {
            lineattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(lineattr);

        Element textattr = document.createElement("textattr");
        for (AttributeType.Attribute attribute : attributeType.getTextAtrributes()) {
            textattr.setAttribute(attribute.attrName,attribute.value);
        }
        trans.appendChild(textattr);

        Element text = document.createElement("text");
        if (textValue != null) {
            text.appendChild(document.createTextNode(textValue));
        }
        trans.appendChild(text);

        return trans;
    }


}
