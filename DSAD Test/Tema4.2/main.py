import numpy as np
import pandas as pd
from pandas import DataFrame

agricultura = pd.read_csv("Agricultura.csv", index_col=0)
populatie = pd.read_csv("PopulatieLocalitati.csv", index_col=0)
roNuts = pd.read_csv("RO_NUTS.csv", index_col=0)
activitati = list(agricultura)[1:]

cerinta1 = agricultura.apply(func= lambda x:x["PlanteNepermanente"]+x["PlanteInmultire"],axis=1)
cerinta1.name = "Plante"

cerinta1_final = pd.DataFrame(cerinta1)
cerinta1_final.insert(0,"Localitate",agricultura["Localitate"])
cerinta1_final.sort_values(by="Plante", ascending=False, inplace=True)

cerinta1_final.to_csv("out/cerinta1.csv")
#print(cerinta1_final)

cerinta2 =agricultura[agricultura.columns[1:]].apply(func = lambda x:x.sum(),axis=1)
cerinta2.name = "Cifra de afaceri"
cerinta2 = pd.DataFrame(cerinta2) # ca sa putem sa inseram coloana trb sa-l facem DataFrame
cerinta2.insert(0,"Localitate",agricultura["Localitate"])
cerinta2.to_csv("out/cerinta2.csv")
#print(cerinta2)

cerinta3_ = cerinta2.merge(populatie[["Judet","Populatie"]], left_index=True, right_index=True)
# cerinta3 = cerinta3_.apply(func=lambda x:x["Cifra de afaceri"]/x["Populatie"],axis=1)
# cerinta3.name = "CA Capita"
# cerinta3 = pd.DataFrame(cerinta3)
# cerinta3.insert(0,"Localitate",agricultura["Localitate"])
cerinta3 = pd.DataFrame(cerinta3_["Localitate"])
cerinta3["CA"] = cerinta3_["Cifra de afaceri"]/cerinta3_["Populatie"]
cerinta3.to_csv("out/cerinta3.csv")


cerinta4_ =agricultura.merge(populatie[["Judet","Populatie"]],left_index=True,right_index=True)
cerinta4 = cerinta4_[activitati + ["Judet"]].groupby(by="Judet").sum()
cerinta4.to_csv("out/cerinta4.csv")
#print(cerinta4)

cerinta5_ = cerinta4_.merge(roNuts["Regiune"],left_on="Judet", right_index=True)

def ponderi(t:pd.DataFrame):
    v = np.average(t.values[:,:-1], weights=t.values[:,-1],axis=0)
    return pd.Series(v, t.columns[:-1])
   # print((t["PlantePermanente"]*t["Populatie"]).sum()/t["Populatie"].sum())

cerinta5 = cerinta5_[activitati + ["Populatie","Regiune"]].groupby(by="Regiune").apply(func=ponderi)
cerinta5.to_csv("out/cerinta5.csv")
#print(cerinta5)





