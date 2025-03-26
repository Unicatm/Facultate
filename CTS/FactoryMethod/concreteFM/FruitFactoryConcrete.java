package concreteFM;


public class FruitFactoryConcrete {
    public Fruct getFruit(String fruitType){
        if(fruitType==null){
            return null;
        }

        if(fruitType.equalsIgnoreCase("MAR")){
            return new Mar();
        }else if(fruitType.equalsIgnoreCase("CAPSUNI")){
            return new Capsuni();
        }

        return null;
    }
}
