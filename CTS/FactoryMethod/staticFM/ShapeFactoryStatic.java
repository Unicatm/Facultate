package staticFM;

public class ShapeFactoryStatic {

    public static Shape getShape(String shapeType){
        if(shapeType==null){
            return null;
        }

        if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        }else if(shapeType.equalsIgnoreCase("SQARE")){
            return new Sqare();
        }

        return null;
    }
}
