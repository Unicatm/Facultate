package tema;

public class LocFactory {
    public ILoc alocareLoc(String tipLoc) throws Exception {
        if(tipLoc.equalsIgnoreCase("BASIC")){
            return new LocBasic();
        }else if(tipLoc.equalsIgnoreCase("ELECTRICITATE")){
            return new LocCuElectricitate();
        }else if(tipLoc.equalsIgnoreCase("EXTRA_UMBRA")){
            return new LocExtraUmbra();
        }else{
            throw new Exception("Tipul de loc "+tipLoc+" nu exista");
        }
    }
}
