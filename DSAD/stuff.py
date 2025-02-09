rs_medie = df_rata["RS"].mean()
cerinta1 = df_rata[df_rata["RS"]<rs_medie]
cerinta1 = pd.concat([cerinta1.iloc[:,0:1],cerinta1.iloc[:,-1]],axis=1)

cerinta2 = df_ind.groupby(df_tari["Continent"]).idxmax()
print(cerinta2)



# A1
df_medie = pd.DataFrame()
df_medie["Consum mediu"] = df_ind.mean(axis="columns")
cerinta1 = pd.concat([df_alcool.loc[:,"Code"],df_medie ],axis=1)
# print(cerinta1)

# A2
df_merge = df_alcool.merge(df_tari,left_index=True,right_index=True)
df_merge = df_merge.iloc[:,1:]
medie = df_merge.groupby("Continent").mean(numeric_only=True)
anul = pd.DataFrame()
anul["Anul"] = medie.idxmax(axis=1)
print(anul)


