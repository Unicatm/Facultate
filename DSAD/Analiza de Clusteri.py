import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sb
from scikitplot.metrics import plot_silhouette
from scipy.cluster.hierarchy import linkage, dendrogram, fcluster
from sklearn.decomposition import PCA
from sklearn.metrics import silhouette_score, silhouette_samples
from sklearn.preprocessing import StandardScaler


def cleanData(df:pd.DataFrame):
    if df.isna().any().any():
        for col in df.columns:
            if df[col].isna().any().any():
                if pd.api.types.is_numeric_dtype(df[col]):
                    df[col]=df[col].fillna(df[col].mean())
                else:
                    df[col]=df[col].fillna(df[col].mode()[0])
    return df


df_tari = pd.read_csv("DateIN/CoduriTariExtins.csv",index_col=0)
df_alcool = pd.read_csv("DateIN/alcohol.csv",index_col=0)

df_tari=cleanData(df_tari)
df_alcool=cleanData(df_alcool)
labels = df_alcool.index.tolist()


df_indicatori = df_alcool.loc[:,"2000":"2018"]
#Standardizare
scaler = StandardScaler()
date_standardizate = scaler.fit_transform(df_indicatori)
df_standardizate = pd.DataFrame(data=date_standardizate,index=df_indicatori.index,columns=df_indicatori.columns)


# MATRICEA DE IERARHIE - ward
linkage_matrix = linkage(date_standardizate,method="ward")
df_linkage_matrix = pd.DataFrame(data=linkage_matrix,columns=["Cluster 1","Cluster 2","Distanta","Nr instante"])

# DENDROGRAMA MATRICEI DE IERARHIE
plt.figure()
plt.title("Dendrograma Ward")
dendrogram(linkage_matrix,labels=labels)
plt.xlabel("Sample data")
plt.ylabel("Distanta")
plt.show()



# NUMAR OPTIM DE CLUSTERI - ELBOW
distance = linkage_matrix[:,2]
differences = np.diff(distance,2)
puncte_elb = np.argmax(differences)+1

# PARTITIONAREA OPTIMA - componenta partitiei optimale
clusters = fcluster(linkage_matrix,t=puncte_elb,criterion="maxclust")
df_alcool["Clusters"] = clusters

# DENDOGRAMA PARTITIONARII OPTIMALE
plt.figure()
plt.title("Dendrograma optima")
dendrogram(linkage_matrix,color_threshold=distance[puncte_elb-1],labels=labels)
plt.xlabel("Sample data")
plt.ylabel("Distanta")
plt.show()



# SILHOUETTE
# Scor Silhouette - nivel global
scoreSilh = silhouette_score(df_standardizate,clusters)
print(f"Scorul Silhouette global {scoreSilh}")

# Score Silhouette - pt fiecare partitie
partitiiSilh = silhouette_samples(df_standardizate,clusters)
print(f"Scorul Silhouette pe partitii {partitiiSilh}")

# PLOT SILHOUETTE
clusters_oarecare = fcluster(linkage_matrix,t=5,criterion="maxclust") # ca sa se vada mai bine plotul
plot_silhouette(df_standardizate,clusters_oarecare)
plt.show()



# HISTOGRAMA PENTRU FIEC. OBS(INDICATOR)
for col in df_standardizate.columns:
    plt.figure()
    plt.title(f"Distributia pe anul {col} pe clusteri")

    for cluster in np.unique(clusters_oarecare):
        sb.histplot(df_standardizate[col][clusters==cluster],kde=True,label=f"Cluster {cluster}")

    plt.xlabel(col)
    plt.ylabel("Frecventa")
    plt.legend()
    plt.show()




# TRASAREA PLOT PARTITIE IN COMPONENTE PRINCIPALE
modelACP = PCA(n_components=2)
C =modelACP.fit_transform(df_standardizate)

plt.figure("Partiiile optime in axe PCA")
plt.title("Partiiile optime in axe PCA")
for cluster in np.unique(clusters):
    plt.scatter(C[clusters==cluster,0],C[clusters==cluster,1])

plt.xlabel("C1")
plt.ylabel("C2")
plt.show()
