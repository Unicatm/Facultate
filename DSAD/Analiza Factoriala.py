from operator import index

import matplotlib.pyplot as plt
import numpy as np
import seaborn as sb
import pandas as pd
from factor_analyzer import FactorAnalyzer, calculate_bartlett_sphericity, calculate_kmo
from sklearn.preprocessing import StandardScaler


def cleanData(df:pd.DataFrame):
    if df.isna().any().any():
        for col in df.columns:
            if df[col].isna().any():
                if pd.api.types.is_numeric_dtype(df[col]):
                    df[col]=df[col].fillna(df[col].mean())
                else:
                    df[col]=df[col].fillna(df[col].mode()[0])
    return df


df_tari = pd.read_csv("DataIN/Coduri_Localitati.csv",index_col=0)
df_voturi = pd.read_csv("DataIN/VotBUN.csv",index_col=0)

df_tari = cleanData(df_tari)
df_voturi=cleanData(df_voturi)

df_indicatori = df_voturi.loc[:,"Barbati_18-24":"Femei_65_"]
#Standardizam
scaler = StandardScaler()
standardizate = scaler.fit_transform(df_indicatori)
df_standardizate = pd.DataFrame(data=standardizate,index=df_voturi.index, columns=df_indicatori.columns)

# ANALIZA FACTORIALA
modelAF = FactorAnalyzer(n_factors=len(df_indicatori),rotation=None)
F = modelAF.fit(standardizate)


# BARTLETT
testul_bartlett = calculate_bartlett_sphericity(df_standardizate)[1]
print(testul_bartlett)
# KMO
testul_kmo = calculate_kmo(df_standardizate)[1]
print(testul_kmo)


# VARIANTA FACTORILOR
varianta = modelAF.get_factor_variance()[0]
print(varianta)


# CORELATII FACT
factori = ["F"+str(i+1) for i in range(len(varianta))]
corr = modelAF.loadings_
df_corr = pd.DataFrame(data=corr, index=df_indicatori.columns,columns=factori)


# CERCUL CORELATIILOR
plt.figure(figsize=(6,6))
plt.title("Cercul corelatiilor")

df_cerc = df_corr[["F1","F2"]]
theta = np.linspace(0,2*np.pi,500)

plt.plot(np.sin(theta),np.cos(theta),color="r")
plt.axhline(y=0)
plt.axvline(x=0)

for i,var in enumerate(df_cerc.index):
    plt.scatter(df_cerc.loc[var,"F1"],df_cerc.loc[var,"F2"])

    plt.text(
        df_cerc.loc[var, "F1"],
        df_cerc.loc[var, "F2"],
        var,
        color="b"
    )

plt.grid()
# plt.show()


# CORELOGRAMA CORELATIILOR
plt.figure()
sb.heatmap(data=df_corr)
# plt.show()


# COMUNALITATI
comm = modelAF.get_communalities()
df_comm = pd.DataFrame(data=comm,index=df_indicatori.columns,columns=["Comunalitati"])


# CORELOGRAMA COMUNALITATI
plt.figure()
sb.heatmap(df_comm)
# plt.show()


# SCORURI
scoruri = modelAF.transform(df_standardizate)
df_scoruri = pd.DataFrame(data=scoruri,index=df_voturi.index,columns=factori)

# PLOT SCORURI
plt.figure()
plt.scatter(df_scoruri["F1"],df_scoruri["F2"])
plt.show()


