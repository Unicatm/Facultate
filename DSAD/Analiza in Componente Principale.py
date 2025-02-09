import matplotlib.pyplot as plt
import numpy as np
import seaborn as sb
import pandas as pd
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler


def cleanData(df:pd.DataFrame):
    if df.isna().any().any():
        for col in df.columns:
            if df[col].isna().any():
                if pd.api.types.is_numeric_dtype(df[col]):
                    df[col] = df[col].fillna(df[col].mean())
                else:
                    df[col] = df[col].fillna(df[col].mode()[0])
    return df


df_tari = pd.read_csv("DateIN/CoduriTariExtins.csv",index_col=0)
df_rata = pd.read_csv("DateIN/Rata.csv",index_col=0)

df_tari=cleanData(df_tari)
df_rata=cleanData(df_rata)

df_ind = df_rata.iloc[:,1:]
# Standardizam datele
scaler = StandardScaler()
df_stand = scaler.fit_transform(df_ind)

# Cream modelul ACP
modelACP = PCA()
C = modelACP.fit_transform(df_stand)

# Varianta
varianta = modelACP.explained_variance_
varianta_cum = np.cumsum(varianta)

# Graficul variantei
plt.figure()
plt.bar(range(1,len(varianta)+1),varianta,label="Varianta",align="center")
plt.step(range(1,len(varianta)+1),varianta_cum,label="Varianta cumulata",where="mid")
plt.axhline(y=0.8,label="80% din variatia cumulata",color="g")
plt.axvline(x=np.argmax(varianta_cum>=0.8)+1,label="Knee",color="y")
plt.grid()
plt.legend()
plt.show()


# SCORURI
componente = ["C"+str(i+1) for i in range(len(varianta))]
df_scoruri = pd.DataFrame(data=C,index=df_ind.index,columns=componente)

plt.figure()
plt.scatter(df_scoruri["C1"],df_scoruri["C2"])
plt.show()

# CORELATII
corr = np.corrcoef(df_stand.T,C.T)[:len(df_ind.columns),len(df_ind.columns):]
df_corr = pd.DataFrame(data=corr, index=df_ind.columns,columns=componente)

# CORELOGRAMA CORELATII
plt.figure()
sb.heatmap(df_corr)
plt.show()


# CERCUL CORELATIILOR
plt.figure(figsize=(6,6))

df_cerc = df_corr[["C1","C2"]]

theta = np.linspace(0,2*np.pi,500)
plt.plot(np.sin(theta),np.cos(theta),color="r")

for i,var in enumerate(df_cerc.index):
    plt.scatter(df_cerc.loc[var,'C1'],df_cerc.loc[var,'C2'])
    plt.text(df_cerc.loc[var, 'C1']*1.1, df_cerc.loc[var, 'C2']*1.1,var)
plt.axvline(color="gray")
plt.axhline(color="gray")
plt.grid()
plt.show()


# COMUNALITATI
comm = np.cumsum(corr**2,axis=1)
df_comm = pd.DataFrame(data=comm, index=df_ind.columns,columns=componente)

# CORELOGRAMA COMUNALITATILOR
plt.figure()
sb.heatmap(df_comm)
plt.show()


# COS
C2 = C**2
cos = (C2.T/np.sum(C2,axis=1)).T
df_cos = pd.DataFrame(data=cos,index=df_ind.index,columns=componente)


# CONTRIBUTII
contrb = (C2/np.sum(C2,axis=0))
df_contrb = pd.DataFrame(data=contrb,index=df_ind.index,columns=componente)
