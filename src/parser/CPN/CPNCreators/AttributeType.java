package parser.CPN.CPNCreators;

import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.List;

public class AttributeType {

    private final static String POS_ATTR = "posattr";
    private final static String FILL_ATTR = "fillattr";
    private final static String LINE_ATTR = "lineattr";
    private final static String TEXT_ATTR = "textattr";

    public final static List<String> basicFieldsList = Arrays.asList(POS_ATTR, FILL_ATTR, LINE_ATTR, TEXT_ATTR);

    private Attribute[] posAtrributes;
    private Attribute[] fillAtrributes;
    private Attribute[] lineAtrributes;
    private Attribute[] textAtrributes;

    public AttributeType(Types type, PositionPicker.TransPositions positions) {
        switch (type) {
            case TRANS:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getTransX()),
                                new Attribute("y", positions.getTransY())};
                fillAtrributes = new Attribute[]
                        {new Attribute("colour", "White"),
                                new Attribute("pattern", "Solid"),
                                new Attribute("filled", "false")};
                lineAtrributes = new Attribute[]
                        {new Attribute("colour", "Black"),
                                new Attribute("thick", "1"),
                                new Attribute("type", "Solid")};
                textAtrributes = new Attribute[]
                        {new Attribute("colour", "Black"),
                                new Attribute("bold", "false")};
                break;
            case CODE:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getCodeX()),
                                new Attribute("y", positions.getCodeY())};
                setStandardAttributes();
                setStandardAttributes();
                break;
            case TIME:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getTimeX()),
                                new Attribute("y", positions.getTimeY())};
                setStandardAttributes();
                break;
            case COND:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getCondX()),
                                new Attribute("y", positions.getCondY())};
                setStandardAttributes();
                break;
            case PRIORITY:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getPriorityX()),
                                new Attribute("y", positions.getPriorityY())};
                setStandardAttributes();
                break;
        }

    }

    public AttributeType(Types type, PositionPicker.PlacePositions positions) {
        switch (type) {
            case PLACE:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getPlaceX()),
                                new Attribute("y", positions.getPlaceY())};
                fillAtrributes = new Attribute[]
                        {new Attribute("colour", "White"),
                                new Attribute("pattern", "Solid"),
                                new Attribute("filled", "false")};
                lineAtrributes = new Attribute[]
                        {new Attribute("colour", "Black"),
                                new Attribute("thick", "1"),
                                new Attribute("type", "Solid")};
                textAtrributes = new Attribute[]
                        {new Attribute("colour", "Black"),
                                new Attribute("bold", "false")};
                break;
            case TYPE:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getTypeX()),
                                new Attribute("y", positions.getTypeY())};
                setStandardAttributes();
                break;
            case INITMARKING:
                posAtrributes = new Attribute[]
                        {new Attribute("x", positions.getInitMarkX()),
                                new Attribute("y", positions.getInitMarkY())};
                setStandardAttributes();
                break;
        }

    }

    public AttributeType(Types type, @Nullable PositionPicker.ArcAnnotationPositions arcAnnotationPositions){
        switch (type){
            case ARC:
                posAtrributes = new Attribute[]
                        {new Attribute("x", "0.00000"),
                                new Attribute("y", "0.00000")};
                setStandardAttributes();
            case ARC_ANNOT:
                if (arcAnnotationPositions!=null) {
                    posAtrributes = new Attribute[]
                            {new Attribute("x", arcAnnotationPositions.getX()),
                                    new Attribute("y", arcAnnotationPositions.getY())};
                }else {
                    posAtrributes = new Attribute[]
                            {new Attribute("x", "0.00000"),
                                    new Attribute("y", "0.00000")};
                    setStandardAttributes();
                }
                setStandardAttributes();
        }
    }

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

    public Attribute[] getCustomAttributes(String attributeIdentifier) {
        switch (attributeIdentifier) {
            case POS_ATTR:
                return getPosAtrributes();
            case FILL_ATTR:
                return getFillAtrributes();
            case LINE_ATTR:
                return getLineAtrributes();
            case TEXT_ATTR:
                return getTextAtrributes();
            default:
                return getPosAtrributes();
        }
    }

    private void setStandardAttributes() {
        fillAtrributes = new Attribute[]
                {new Attribute("colour", "White"),
                        new Attribute("pattern", "Solid"),
                        new Attribute("filled", "false")};
        lineAtrributes = new Attribute[]
                {new Attribute("colour", "Black"),
                        new Attribute("thick", "0"),
                        new Attribute("type", "Solid")};
        textAtrributes = new Attribute[]
                {new Attribute("colour", "Black"),
                        new Attribute("bold", "false")};
    }

    public enum Types {
        TRANS,
        COND,
        TIME,
        CODE,
        PRIORITY,
        TYPE,
        INITMARKING,
        PLACE,
        ARC,
        ARC_ANNOT
    }

    class Attribute {
        public String attrName;
        public String value;

        public Attribute(String attrName, String value) {
            this.attrName = attrName;
            this.value = value;
        }
    }

}
