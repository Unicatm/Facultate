package staticFM;

public class Client {
    public static void main(String[] args) {
        Shape shape1 = ShapeFactoryStatic.getShape("SQARE");
        shape1.draw();
    }
}
