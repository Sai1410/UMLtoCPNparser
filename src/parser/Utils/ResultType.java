package parser.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultType {

    private final static List<String> typesList = Arrays.asList(
            "UML file not specifed",
            "Output directory not specified",
            "UML file not recognized",
            "Ready to parse",
            "Parsing error",
            "CPN file generated"
            );

    private Type type;

    public ResultType(Type type) {
        this.type = type;
    }

    private void setType(Type type){
        this.type = type;
    }

    public boolean isTypeGood(){
        if (type.getValue() == 3 || type.getValue() == 5){
            return true;
        }else {
            return false;
        }
    }

    public String getResult(){
        return typesList.get(type.getValue());
    }

    public enum Type{
        UML_NOT_PICKED(0),
        OUTPUT_NOT_PICKED(1),
        UML_ERROR(2),
        READY_TO_PARSE(3),
        PARSING_ERROR(4),
        GENERATED(5);

        private int value;
        private static Map map = new HashMap<>();

        Type(int value) {
            this.value = value;
        }

        static {
            for (Type type1 : Type.values()) {
                map.put(type1.value, type1);
            }
        }

        public static Type valueOf(int type){
            return (Type) map.get(true);
        }

        public int getValue(){
            return value;
        }
    }
}
