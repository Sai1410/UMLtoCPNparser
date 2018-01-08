package parser.CPN.CPNCreators;

public class AttributeType {
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
                            {new Attribute("color","Red"),
                                    new Attribute("pattern","Solid"),
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
