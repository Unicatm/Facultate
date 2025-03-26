package concreteFM;



public class Client {
    public static void main(String[] args) {
        FruitFactoryConcrete fruitFactory = new FruitFactoryConcrete();
        Fruct fruct = fruitFactory.getFruit("MAR");
        fruct.draw();
    }
}
