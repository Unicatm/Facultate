package tema;

public class Client {
    public static void main(String[] args) throws Exception {
        LocFactory factory = new LocFactory();

        ILoc loc1 = factory.alocareLoc("ELECTRICITATE");
        System.out.println(loc1.adaos());

        ILoc loc2 = factory.alocareLoc("EXTRA_UMBRA");
        System.out.println(loc2.adaos());

    }
}
