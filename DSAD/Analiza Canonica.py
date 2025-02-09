import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sb
from PIL.ImageChops import offset
from scipy.stats import chi2
from sklearn.cross_decomposition import CCA



def cleanData(dataDF):
    isinstance(dataDF, pd.DataFrame)
    if dataDF.isna().any().any():
        for col in dataDF.columns:
            if dataDF[col].isna().any():
                if pd.api.types.is_numeric_dtype(dataDF[col]):
                    dataDF[col] = dataDF[col].fillna(dataDF[col].mean())
                else:
                    dataDF[col] = dataDF[col].fillna(dataDF[col].mode()[0])
    return dataDF


# Citire date
coduricsv = pd.read_csv("Coduri_localitati.csv", index_col=0)
voturicsv = pd.read_csv("Vot.csv", index_col=0)

df_coduri = cleanData(coduricsv)
df_voturi = cleanData(voturicsv)


# Impartirea variabilelor - femei/barbati
X = df_voturi.iloc[:,2:6]
Y = df_voturi.iloc[:,6:]

# Facem analiza canonica
modelCCA = CCA(n_components=4)
modelCCA.fit(X,Y)

# 1. CALCULUL SCORURURILOR CANONICE
X_scoruri, Y_scoruri = modelCCA.transform(X,Y)
coloaneX = [f"X_scor_{i+1}" for i in range(X_scoruri.shape[1])]
coloaneY = [f"Y_scor_{i+1}" for i in range(Y_scoruri.shape[1])]
df_x_scoruri = pd.DataFrame(data=X_scoruri,columns=coloaneX)
df_y_scoruri = pd.DataFrame(data=Y_scoruri,columns=coloaneY)


# 2. CALCULUL CORELATIILOR CANONICE
corr_canonice = np.corrcoef(X_scoruri.T,Y_scoruri.T).diagonal(offset=X_scoruri.shape[1])
perechi_canonice = [f"Pereche {i+1}" for i in range(len(corr_canonice))]
df_corr_canonice = pd.DataFrame(data={"Pereche canonica":perechi_canonice,"Corelatia canonica":corr_canonice})


# 3. CORELATII VAR OBS - VAR CANONICE
corr_x_xscoruri = np.corrcoef(X.T,X_scoruri.T)[:X.shape[1],X.shape[1]:]
corr_y_yscoruri = np.corrcoef(Y.T,Y_scoruri.T)[:Y.shape[1],Y.shape[1]:]

df_corr_x = pd.DataFrame(data=corr_x_xscoruri,index=X.columns,columns=coloaneX)
df_corr_y = pd.DataFrame(data=corr_y_yscoruri,index=Y.columns,columns=coloaneY)


# 4. VARIANTA EXPLICATA SI REDUNDANTA INFORMATIONALA
var_expl = corr_canonice**2
var_expl_x=np.var(X_scoruri,axis=0)/np.sum(np.var(X,axis=0))
var_expl_y=np.var(Y_scoruri,axis=0)/np.sum(np.var(Y,axis=0))

redundanta_x=np.sum(var_expl*var_expl_x)
redundanta_y=np.sum(var_expl*var_expl_y)

print("Redundanța informațională X:", redundanta_x)
print("Redundanța informațională Y:", redundanta_y)

# 5. CORELOGRAME
plt.figure()
plt.title("Corelogrma variabilelor x - Scoruri canonice")
sb.heatmap(df_corr_x,annot=True,cmap="coolwarm",center=0)

plt.figure()
plt.title("Corelogrma variabilelor x - Scoruri canonice")
sb.heatmap(df_corr_y,annot=True,cmap="coolwarm",center=0)

plt.show()


# 6. CERCUL CORELATIILOR VAR OBS - VAR CANONICE
plt.figure(figsize=(6,6))
plt.title("Cercul corelatiilor")

theta = np.linspace(0,2*np.pi,500)
plt.plot(np.sin(theta),np.cos(theta),color="r")
plt.axhline(color="gray")
plt.axvline(color="gray")

for var in df_corr_x.index:
    plt.scatter(df_corr_x.loc[var,"X_scor_1"],df_corr_x.loc[var,"X_scor_2"])
    plt.text(df_corr_x.loc[var, "X_scor_1"]*1.1, df_corr_x.loc[var, "X_scor_2"]*1.1,var)

for var in df_corr_y.index:
    plt.scatter(df_corr_y.loc[var,"Y_scor_1"],df_corr_y.loc[var,"Y_scor_2"])
    plt.text(df_corr_y.loc[var, "Y_scor_1"]*1.1, df_corr_y.loc[var, "Y_scor_2"]*1.1,var)


plt.grid()
plt.show()


# 7. TESTUL BARTLETT
def test_bartlett(corr,n,p,q):
    wilks = np.prod(1-corr**2)
    df = p*q
    chi2_stat = -(n-1-(p+q+1)/2)*np.log(wilks)
    p_value = 1  - chi2.cdf(chi2_stat,df)

    print(f"Wilks' Lambda: {wilks}")
    print(f"Chi-Square: {chi2_stat}")
    print(f"p-value: {p_value}")

    return wilks,chi2_stat,p_value

n,p,q=X.shape[0],X.shape[1],Y.shape[1]
test_bartlett(corr_canonice,n,p,q)

