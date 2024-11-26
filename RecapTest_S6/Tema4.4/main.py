import numpy as np
import pandas as pd

educatie = pd.read_csv("Educatie.csv", index_col=0)
popJud = pd.read_csv("PopulatieJudete.csv",index_col=0)
roNuts = pd.read_csv("RO_NUTS.csv",index_col=0)

tipuri = educatie.columns


ponderi = educatie.apply(func =lambda x:x/x.sum(),axis=1)
cerinta1 = ponderi[["PersoaneAnalfabete"]].sort_values(by="PersoaneAnalfabete",ascending=False)
cerinta1.to_csv("out/Cerinta1.csv")


cerinta2 = educatie[["FaraScoala","PersoaneAnalfabete"]]
cerinta2 = cerinta2.merge(popJud, left_index=True,right_index=True).apply(func = lambda x:((x["PersoaneAnalfabete"]+x["FaraScoala"])*100000)/x["Populatie"],axis=1)
cerinta2.name = "FaraScoala_Analfabeti"
cerinta2 = pd.DataFrame(cerinta2)
cerinta2 = cerinta2.sort_values("FaraScoala_Analfabeti",ascending=False)
cerinta2.to_csv("out/Cerinta2.csv")

cerinta3 =educatie.merge(roNuts[["Regiune"]], left_index=True, right_index=True).groupby(by="Regiune").sum()
cerinta3.to_csv("out/Cerinta3.csv")


def func4(t:pd.DataFrame):
    x = t.values
    poz = np.argmax(x,axis=1)
    v = t.columns[poz]

    return pd.Series(v,index = t.index)

# cerinta4 = educatie.merge(roNuts[["Regiune"]], left_index=True, right_index=True).apply(func = func4)

# cerinta4 = educatie.merge(roNuts[["Regiune"]], left_index=True, right_index=True)
# col = cerinta4.pop("Regiune")
# cerinta4.insert(0,"Regiune",col)
# cerinta4 = educatie.apply(func = func4)
# print(cerinta4)

#cerinta4 = cerinta4.apply(func = func4)

def ponderi(t:pd.DataFrame):
    v = np.average(t.values[:,:-1], weights=t.values[:,-1],axis=0)
    return pd.Series(v, t.columns[:-1])

educatie_ = educatie.merge(popJud,left_index=True, right_index=True)
cerinta5_ = educatie_.merge(roNuts["Regiune"], left_index=True, right_index=True)
cerinta5 = cerinta5_.groupby(by="Regiune").apply(func=ponderi)
cerinta5.to_csv("out/Cerinta5.csv")
print(cerinta5)
