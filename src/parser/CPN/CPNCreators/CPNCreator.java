package parser.CPN.CPNCreators;

import com.sun.istack.internal.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CPNCreator {

    protected Element addBasicFields(Element element, Document document, AttributeType attributeType, @Nullable String textValue){
        for (String tagName : AttributeType.basicFieldsList) {
            Element innerElem = document.createElement(tagName);
            for (AttributeType.Attribute attribute : attributeType.getCustomAttributes(tagName)) {
                innerElem.setAttribute(attribute.attrName,attribute.value);
            }
            element.appendChild(innerElem);
        }

        Element text = document.createElement("text");
        if (textValue != null) {
            text.appendChild(document.createTextNode(textValue));
        }
        element.appendChild(text);

        return element;
    }
}
