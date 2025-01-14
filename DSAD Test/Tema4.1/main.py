import pandas as pd
import numpy as np

energLoc = pd.read_csv("EnergLoc.csv", index_col=0)
roNuts = pd.read_csv("RO_NUTS.csv", index_col=0)

#Media pe linie a coeficientilor --- axis = 1 -> pe linii
cerinta1 = energLoc.apply(func=lambda x:x.mean(),axis=1).round(4)
cerinta1.name = "CoefLocalizare"
cerinta1.to_csv("data_out/Cerinta1.csv")

#Judetele cu c3511 mai mare decat 1
cerinta2 = energLoc[energLoc["c3511"]>1].round(4)
cerinta2.to_csv("data_out/Cerinta2.csv")

# Afisaza coeficientul cel mai mare de pe linie + judet
# x.argmax() returneaza pozitia celei mai mari valori de pe linie
# x.index[] returneaza numele coloanei pe care se afla cea mai mare valoare
cerinta3 = energLoc.apply(func = lambda x:x.index[x.argmax()] ,axis=1)
cerinta3.to_csv("data_out/Cerinta3.csv")

energLoc_ = energLoc.merge(roNuts["Regiune"],left_index=True, right_index=True)
cerinta4 = energLoc_.groupby(by = "Regiune").mean().round(4)
cerinta4.to_csv("data_out/Cerinta4.csv")

def func1(t:pd.DataFrame):
     x = t.values
     max_indexes = np.argmax(x,axis=0)
     max_nameCol = t.index[max_indexes]
     return pd.Series(max_nameCol,t.columns)


cerinta5 = energLoc_.groupby(by="Regiune").apply(func = func1)
del cerinta5[cerinta5.columns[-1]] #am sters ultima coloana
cerinta5.to_csv("data_out/Cerinta5.csv")

print(cerinta5)


