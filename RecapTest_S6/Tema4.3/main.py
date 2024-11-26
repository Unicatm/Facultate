import pandas as pd
import numpy as np

rataFM = pd.read_csv("RataFM.csv")
roNuts = pd.read_csv("RO_NUTS.csv",index_col=0)

cerinta1_ = rataFM[rataFM["Anul"]==2020]
cerinta1 = cerinta1_[["IndicativJudet","Valoare"]]
# cerinta1 = pd.DataFrame()
# cerinta1.insert(0,"IndicativJudet",cerinta1_["IndicativJudet"])
# cerinta1.insert(1,"Valoare",cerinta1_["Valoare"])
cerinta1.to_csv("out/Cerinta1.cvs",index=False)

cerinta2 = rataFM[["IndicativJudet","Valoare"]].groupby(by="IndicativJudet").mean().sort_values("Valoare",ascending=False)
cerinta2.to_csv("out/Cerinta2.cvs",index=False)

def func1(t:pd.DataFrame):
    k = t["Valoare"].argmax()
    return t["Anul"].iloc[k]

cerinta3 = rataFM.groupby(by = "IndicativJudet").apply(func = func1)
#cerinta3 = rataFM.groupby(by = "IndicativJudet").apply(lambda x: x.loc[x["Valoare"].idxmax(), "Anul"])
cerinta3.to_csv("out/Cerinta3.cvs",index=False)

rataPivot = rataFM.pivot(index = "IndicativJudet",columns="Anul", values="Valoare")
cerinta4 =rataPivot.merge(roNuts["Regiune"], left_index=True,right_index=True).groupby(by="Regiune").mean()
cerinta4.to_csv("out/Cerinta4.cvs")
print(cerinta4)

def func5(t:pd.DataFrame):
    x = t.values
    argm = np.argmax(x, axis=0)
    poz = t.index[argm]

    return pd.Series(poz,t.columns)

cerinta5 = rataPivot.merge(roNuts["Regiune"], left_index=True,right_index=True).groupby("Regiune").apply(func = func5)
print(cerinta5)