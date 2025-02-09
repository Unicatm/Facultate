import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sb
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.metrics import confusion_matrix, accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler


def cleanData(df):
    if df.isna().any().any():
        for col in df.columns:
            if pd.api.types.is_numeric_dtype(df[col]):
                df[col] = df[col].fillna(df[col].mean())
            else:
                df[col] = df[col].fillna(df[col].mode()[0])
    return df


df_proiect = pd.read_csv("ProiectB.csv",index_col=0)
df_apply = pd.read_csv("ProiectB_apply.csv",index_col=0)

df_proiect=cleanData(df_proiect)
df_apply=cleanData(df_apply)


# Separam variabilele
X = df_proiect.iloc[:,1:-1]
Y = df_proiect.iloc[:,-1]

# Standardizam datele
scaler = StandardScaler()
X = scaler.fit_transform(X)
X_apply = scaler.fit_transform(df_apply.iloc[:,1:])

# Impartim setul de date in unul de antrenare si unul de testare
X_train,X_test,Y_train,Y_test = train_test_split(X,Y,test_size=0.3,random_state=42)

# Cream modelul ADL
modelLDA =LinearDiscriminantAnalysis()
modelLDA.fit(X_train,Y_train)

# Evaluarea modelului
Y_pred = modelLDA.predict(X_test)
conf_matrix = confusion_matrix(Y_test,Y_pred)

accuracy = accuracy_score(Y_test,Y_pred)

accuracy_per_class = conf_matrix.diagonal()/ conf_matrix.sum(axis=1)
accuracy_mean = np.mean(accuracy_per_class)


# PREDICTIE PE SETUL DE ANTRENAMENT
Y_train_pred= modelLDA.predict(X_train)
df_train_pred = pd.DataFrame({"Real:":Y_train,"Predict":Y_train_pred})


# SCORURILE DISCRIMINANTE
X_train_lda = modelLDA.transform(X_train)
X_apply_lda = modelLDA.transform(X_apply)

# PLOT
# for i in range(X_train_lda.shape[1]):
#     for label in np.unique(Y_train):
#         sb.kdeplot(X_train_lda[Y_train==label,i],fill=True,warn_singular=False,label=f"{label} Axa {i+1}")
#     plt.title(f"Distributia pe axa discriminanta {i+1}")
#     plt.xlabel(f"Axa discr {i+1}")
#     plt.legend()
#     plt.show()


# GRAFICUL IN PRIMELE 2 AXE
plt.figure()
for label in np.unique(Y_train):
    plt.scatter(X_train_lda[Y_train==label,0],X_train_lda[Y_train==label,1])
plt.title("GRAFICUL SCORURILOR DISCRIMINANTE IN PRIMELE 2 AXE")
plt.xlabel("Axa 1")
plt.ylabel("Axa 2")
plt.grid()
plt.show()