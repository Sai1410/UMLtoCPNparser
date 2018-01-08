package parser.CPN.CPNCreators;

public class IdCreator {
    private final static String idPrefix = "ID";
    private final static long startingColorId = 1412324325;
    private static long currentColorId;

    private static IdCreator instance;

    private IdCreator(){
        long randomStart = (long) (Math.random() * 10000000);
        currentColorId = startingColorId + randomStart;
    };

    public static IdCreator getInstance(){
        if (instance != null){
            return instance;
        }else {
            instance = new IdCreator();
        }
        return instance;
    }

    private long getIncrementedId(){
        currentColorId++;
        return currentColorId;
    }

    public String getNewId(){
        return idPrefix + getIncrementedId();
    }

}
