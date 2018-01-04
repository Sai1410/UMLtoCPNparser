package parser.UML;

public class ValueExtractor {

    private static String prefixSeparator = "::";
    private static String typeSeparator = "#";
    private static String typeEndSequence = ")";

    public static String getTruncatedName(Object object) {
        String objectName = object.toString();
        objectName = loosePrefix(objectName);
        return objectName;
    }


    private static String loosePrefix(String string) {
        if (string.contains(prefixSeparator)){
            string = string.substring(string.lastIndexOf(prefixSeparator)+prefixSeparator.length());
        }
        return string;
    }

    public static String extractType(Object object) {
        String objectType = object.toString();
        if (objectType.contains(typeSeparator)){
            objectType = objectType.substring(objectType.lastIndexOf(typeSeparator)+typeSeparator.length(),objectType.lastIndexOf(typeEndSequence));
        }
        return objectType;
    }
}